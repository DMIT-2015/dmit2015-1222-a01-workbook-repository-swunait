package dmit2015.faces;

import dmit2015.model.Rectangle;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

//@Named("currentRectangleRequestScopedController")
//@RequestScoped  // create this object for one HTTP request and destroy after the HTTP response has been sent
public class RectangleRequestScopedController {

    @Getter @Setter
    private Rectangle currentRectangle = new Rectangle();

    public String calculateArea() {
        Messages.addGlobalInfo("Area of rectangle = {0}", currentRectangle.area());
        return null;
    }

    public String calculateDiagonal() {
        Messages.addGlobalInfo("Diagonal of rectangle = {0}", currentRectangle.diagonal());
        return null;
    }

    public String calculatePerimeter() {
        Messages.addGlobalInfo("Perimeter of rectangle = {0}", currentRectangle.perimeter());
        return null;
    }

}