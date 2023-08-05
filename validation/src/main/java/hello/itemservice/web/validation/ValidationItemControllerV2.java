package hello.itemservice.web.validation;

import ch.qos.logback.classic.Logger;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
@Slf4j
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // BindingResult bindingResult 파라미터의 위치는 @ModelAttribute Item item 다음에 와야 한다.

        /**
         * Start Validator Using
         *
         * BindingResult
         *
         * , FieldError
         *      생성자 1
         *      objectName : @ModelAttribute 이름
         *      field : 오류가 발생한 필드 이름
         *      defaultMessage : 오류 기본 메시지
         *      ex ) new FieldError("item", "itemName", "상품 이름은 필수입니다.")
         *
         * , ObjectError
         *      objectName : @ModelAttribute 의 이름
         *      defaultMessage : 오류 기본 메시지
         *      특정 필드를 넘어서는 오류가 있으면 ObjectError 객체를 생성해서 bindingResult 에 담아두면 된다
         **/

        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다."));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price","가격은 1,000~1,000,000 까지 허용합니다."));
        }

        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999 까지 허용합니다."));
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000) {
                bindingResult.addError(new ObjectError("item",
                        "가격 + 수량의 합은 10,000 원 이상이어야 합니다." + "현재 값 = "+ result));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors : {}", bindingResult);
            return "validation/v2/addForm";
        }

        /** Success Logic **/
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        /**
        * FieldError
        *      생성자 2 ( 오류 발생 시, 사용자가 입력한 값을 유지하기 위해 )
        *      objectName : 오류가 발생한 객체 이름
        *      field : 오류 필드
        *      rejectedValue : *** 사용자가 입력한 값(거절된 값)
        *      bindingFailure : 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값. 여기서는 바인딩이 실패한 것은 아니기 때문에 false
        *      codes : 메시지 코드
        *      arguments : 메시지에서 사용하는 인자
        *      defaultMessage : 기본 오류 메시지
        **/

        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(),
                    false, null, null,  "상품 이름은 필수입니다."));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(),
                    false, null, null, "가격은 1,000~1,000,000 까지 허용합니다."));
        }

        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            bindingResult.addError(new FieldError("item","quantity", item.getQuantity(),
                    false, null, null, "수량은 최대 9,999 까지 허용합니다."));
        }

        if (item.getPrice() != null && item.getQuantity() != null) {
            int result = item.getPrice() * item.getQuantity();
            if (result < 10000) {
                bindingResult.addError(new ObjectError("item", null, null,
                        "가격 + 수량의 합은 10,000 원 이상이어야 합니다." + "현재 값 = "+ result));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors : {}", bindingResult);
            return "validation/v2/addForm";
        }

        // Success Logic
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    /** errors.properties 메시지 활용 **/
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        /**
         * codes : required.item.itemName 를 사용해서 메시지 코드를 지정한다. 메시지 코드는 하나가 아니라
         * 배열로 여러 값을 전달할 수 있는데, 순서대로 매칭해서 처음 매칭되는 메시지가 사용된다.
         * arguments : Object[]{1000, 1000000} 를 사용해서 코드의 {0} , {1} 로 치환할 값을 전달한다.
         **/

        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName",
                    item.getItemName(), false, new String[]{"required.item.itemName"}, null,
                    null));
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() >
                1000000) {
            bindingResult.addError(new FieldError("item", "price", item.getPrice(),
                    false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }

        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            bindingResult.addError(new FieldError("item", "quantity",
                    item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]
                    {9999}, null));
        }

        //특정 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", new String[]
                        {"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        // Success Logic
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    /** Use BindingResult.rejectValue() **/
//    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        /**
         * rejectValue
         *      field : 오류 필드명
         *      errorCode : 오류 코드(이 오류 코드는 메시지에 등록된 코드가 아니다. 뒤에서 설명할 messageResolver 를 위한 오류 코드이다.)
         *      errorArgs : 오류 메시지에서 {0} 을 치환하기 위한 값
         *      defaultMessage : 오류 메시지를 찾을 수 없을 때 사용하는 기본 메시지
         *
         *      rejectValue() 를 사용하고 부터는 오류 코드를 range 로 간단하게 입력했다. 그래도 오류 메시지를 잘 찾아서 출력한다.
         *
         * MessageCodesResolver
         *  우선순위
         *      #Level1
         *      required.item.itemName: 상품 이름은 필수 입니다.
         *      #Level2
         *      required: 필수 값 입니다.
         **/

        log.info("objectName={}", bindingResult.getObjectName()); // Item : @ModelAttribute name
        log.info("target={}", bindingResult.getTarget()); // Item(id=null, itemName=상품, price=100, quantity=1234)

        if (!StringUtils.hasText(item.getItemName())) {
            bindingResult.rejectValue("itemName", "required");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range",
                    new Object[]{1000, 1000000}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() > 10000) {
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        // 특정 필드 예외가 아닌 전체 예외
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    /** 1. Validator 검증기를 직접 불러서 사용 **/
    private final ItemValidator itemValidator;

//    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        itemValidator.validate(item, bindingResult);

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }
        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }

    /**
     * 2. Validator
     * WebDataBinder : 스프링의 파라미터 바인딩의 역할을 해주고 검증 기능도 내부에 포함
     **/
    @InitBinder // @InitBinder : 해당 컨트롤러에만 영향을 준다. 글로벌 설정은 별도로 해야한다.
    public void init(WebDataBinder dataBinder) {
        log.info("init binder :  {}", dataBinder);
        dataBinder.addValidators(itemValidator);
    }

    /* 글로벌 설정 방법

    기존 컨트롤러의 @InitBinder 를 제거해도 글로벌 설정으로 정상동작.

    @SpringBootApplication
        public class ItemServiceApplication implements WebMvcConfigurer {
             public static void main(String[] args) {
                SpringApplication.run(ItemServiceApplication.class, args);
             }
             @Override
                 public Validator getValidator() {
                 return new ItemValidator();
             }
        }
    */

    /**
     * validator 를 직접 호출하는 부분이 사라지고, 대신에 검증 대상 앞에 @Validated 가 붙었다.
     *
     * @Validated : WebDataBinder 에 등록한 검증기를 찾아서 실행.
     *
     * 여러 검증기를 등록한다면, 그 중에 어떤 검증기가 실행되어야 할 지 구분이 필요.
     * ItemValidator 안 supports() 가 먼저 실행되고, return 값이 true 이면 밑의 validator() 가 실행 됨.
     *
     * **/
    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "validation/v2/addForm";
        }

        //성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }






    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }








}

