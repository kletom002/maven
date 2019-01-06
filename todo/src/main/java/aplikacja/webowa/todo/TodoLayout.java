package aplikacja.webowa.todo;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.VerticalLayout;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.spring.annotation.UIScope;
import java.util.stream.Collectors;
/**
 *
 * @author Syla
 */
@UIScope
@SpringComponent
public class TodoLayout extends VerticalLayout implements TodoChangeListener{

    @Autowired
    TodoRepository repo;
    private List<Todo> todos;

    @PostConstruct
    void init() {
        setWidth("80%");
        update();
    }

    private void update() {
        setTodos(repo.findAll());
    }

    private void setTodos(List<Todo> todos) {
        this.todos = todos;
        removeAllComponents();
        todos.forEach(todo -> addComponent(new TodoItemLayout(todo, this)));
    }

    public void add(Todo todo) {
        repo.save(todo);
        update();
    }

    @Override
    public void todoChanged(Todo todo) {
        add(todo);
    }

    void deleteCompleted() {
        repo.deleteByDone(true);
        update();
    }

}
