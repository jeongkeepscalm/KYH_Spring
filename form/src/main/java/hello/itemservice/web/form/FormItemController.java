package hello.itemservice.web.form;

import hello.itemservice.domain.item.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
@Slf4j
public class FormItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/item";
    }

    // th:object 를 적용하려면 먼저 해당 객체 정보를 넘겨주어야 한다.
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {

        log.info("item.getOpen() : {}", item.getOpen());
        log.info("item.getRegions() : {}", item.getRegions()); // 리스트에 담김.
        log.info("item.getItemType() : {}", item.getItemType());

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/form/items/{itemId}";
    }


    /**
     * @ModelAttribute 는 Controller 안에 있는 별도의 메소드를 적용할 수 있다.
     * 컨트롤러를 요청할 때 regions 에서 반환한 값이 자동으로 모델( model )에 담기게 된다.
     **/
    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("SEOUL", "서울");
        map.put("BUSAN", "부산");
        map.put("JEJU", "제주");
        return map;
    }

    /**
     * ENUM 활용
     * itemTypes 를 등록 폼, 조회, 수정 폼에서 모두 사용하므로 @ModelAttribute 의 특별한 사용법을 적용하자.
     **/
    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values(); // 해당 ENUM 의 모든 정보를 배열로 반환 ex) [BOOK, FOOD, ETC]
    }


    /**
     * 자바 객체 활용
     * DeliveryCode 를 등록 폼, 조회, 수정 폼에서 모두 사용하므로 @ModelAttribute 의 특별한 사용법을 적용하자.
     * <p>
     * 컨트롤러가 호출 될 때 마다 사용되므로
     * deliveryCodes 객체도 계속 생성된다. 이런 부분은 미리 생성해두고 재사용하는 것이 더 효율적이다
     **/
    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> list = new ArrayList<>();
        list.add(new DeliveryCode("FAST", "빠른 배송"));
        list.add(new DeliveryCode("NORMAL", "일반 배송"));
        list.add(new DeliveryCode("SLOW", "느린 배송"));
        return list;
    }


    @ModelAttribute("testMap")
    public Map<String, String> testMap (){
        Map<String, String> map = new LinkedHashMap<>();
        map.put("test1", "테스트1");
        map.put("test2", "테스트2");
        map.put("test3", "테스트3");
        return map;
    }

    @ModelAttribute("testEnum")
    public TestEnum[] testEnums() {
        return TestEnum.values();
    }


    @ModelAttribute("testObject")
    public List<TestObject> testObjects(){
        List<TestObject> list = new ArrayList<>();
        list.add(new TestObject("1", "독수리"));
        list.add(new TestObject("2", "참새"));
        list.add(new TestObject("3", "병아리"));
        return list;
    }
}

