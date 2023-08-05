package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item foundItem = itemRepository.findById(itemId);
        model.addAttribute("item", foundItem);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    /*
    @PostMapping("/add")
    public String addItem(@RequestParam String itemName,
                            @RequestParam int price,
                            @RequestParam Integer quantity,
                            Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute("item2") Item item) {
        itemRepository.save(item);
        // model.addAttribute("item2", item); @ModelAttribute("item2") - ModelAttribute 에서 지정한 item2 로 모델에 담아 넘겨주는 역할.
        return "basic/item";
    }


    @PostMapping("/add")
    public String addItem(Item item) { // @ModelAttribute 생략가능.. 선호 x
        itemRepository.save(item);
        return "basic/item";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
        // 인코딩 문제 야기.
    }
    */

    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";

        // RedirectAttributes 매개변수를 사용하여 PathVariable 처럼 사용가능하며 인코딩 문제 해결.
        // status 는 뭐리파라미터로 출력.
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";

        // edit 호출하고 상태코드 302
        // *** 프로세스 실행 후 url이 /{itemId}/edit 이므로 redirect 로 url 변경
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct // 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다.
    public void init() {

        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}