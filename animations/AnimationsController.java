package animations;

public class AnimationsController {

    private Animation[] animations;
    public final int numberOfAnimations;

    public AnimationsController(int numberOfAnimations) {
        animations = new Animation[numberOfAnimations];
        this.numberOfAnimations = numberOfAnimations;
    }

    public Animation getAnimation(int index) {
        if (index + 1 > animations.length) {
            index = 0;
        }
        return animations[index];
    }

    public void setAnimation(int index, Animation newAnimation) {
        if (index + 1 > numberOfAnimations) {
            index = 0;
        }
        animations[index] = newAnimation;
    }
    
    public Animation getAnimation() {
        return animations[0];
    }

    public void setAnimation(Animation newAnimation) {
        animations[0] = newAnimation;
    }
    
    public void clickAll() {
        for (int n = 0; n < numberOfAnimations; n++) {
            if (getAnimation(n) != null) {
                getAnimation(n).click();
            }
        }
    }

    public void resetAll() {
        for (int n = 0; n < numberOfAnimations; n++) {
            if (getAnimation(n) != null) {
                getAnimation(n).reset();
            }
        }
    }

    public boolean getAnyWasShot() {
        for (int n = 0; n < numberOfAnimations; n++) {
            if (getAnimation(n) != null) {
                if (getAnimation(n).getWasShot()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean getAnyWasClick() {
        for (int n = 0; n < numberOfAnimations; n++) {
            if (getAnimation(n) != null) {
                if (getAnimation(n).getWasClick()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasAnimations() {
        for (int n = 0; n < numberOfAnimations; n++) {
            if (getAnimation(n) != null) {
                return true;

            }
        }
        return false;
    }

}
