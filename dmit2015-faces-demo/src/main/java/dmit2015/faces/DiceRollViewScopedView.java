package dmit2015.faces;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import java.io.Serializable;
import java.util.random.RandomGenerator;

@Named("currentDiceRollViewScopedView")
@ViewScoped // create this object for one HTTP request and keep in memory if the next is for the same page
// class must implement Serializable
public class DiceRollViewScopedView implements Serializable {

    // Declare read/write properties (field + getter + setter) for each form field
    @Getter @Setter
    private Double betAmount;

    @Getter @Setter
    private Integer betFaceValue;

    // Declare read only properties (field + getter) for data sources
    @Getter
    private Integer currentDiceFaceValue;

    @Getter
    private String currentDiceFaceValueImage;
    // Declare private fields for internal usage only objects
    private String[] _diceImages = {
            "resources/img/delapouite/dice-shield.png",
            "resources/img/delapouite/dice-six-faces-one.png",
            "resources/img/delapouite/dice-six-faces-two.png",
            "resources/img/delapouite/dice-six-faces-three.png",
            "resources/img/delapouite/dice-six-faces-four.png",
            "resources/img/delapouite/dice-six-faces-five.png",
            "resources/img/delapouite/dice-six-faces-six.png",
    };

    @PostConstruct // This method is executed after DI is completed (fields with @Inject now have values)
    public void init() { // Use this method to initialized fields instead of a constructor
        // Code to access fields annotated with @Inject

    }

    public void onRollDice() {
        // Generate a random number between 1 and 6
        var rand = RandomGenerator.getDefault();
        currentDiceFaceValue = rand.nextInt(1, 7);
        currentDiceFaceValueImage = _diceImages[currentDiceFaceValue];

        // Check if betFaceValue matches currentFaceValue
        if (betFaceValue == currentDiceFaceValue) {
            Messages.addGlobalInfo("Congratulations you won {0}", betAmount);
        } else {
            Messages.addGlobalFatal("Sorry but you lost {0}. Maybe you shouldn't be gambling.", betAmount);
        }


//        String faceValueString = "one";
//        if (currentDiceFaceValue == 1) {
//            faceValueString = "one";
//        } else if (currentDiceFaceValue == 2) {
//            faceValueString = "two";
//        } else if (currentDiceFaceValue == 3) {
//            faceValueString = "three";
//        } else if (currentDiceFaceValue == 4) {
//            faceValueString = "four";
//        } else if (currentDiceFaceValue == 5) {
//            faceValueString = "five";
//        } else {
//            faceValueString = "six";
//        }
//        currentDiceFaceValueImage = String.format("resources/img/delapouite/dice-six-faces-%s.png", faceValueString);
    }
    public void onClear() {
        // Set all fields to default values
        currentDiceFaceValue = null;
        currentDiceFaceValueImage = null;
    }
}