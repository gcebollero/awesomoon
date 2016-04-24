package zgz.nasa.spaceapps.awesomoon.Tipes;

/**
 * Created by novales35 on 24/04/16.
 */
public class Question {
    private String text,correct,error1,error2,error3;

    public String getCorrect() {
        return correct;
    }

    public String getError1() {
        return error1;
    }

    public String getError2() {
        return error2;
    }

    public String getError3() {
        return error3;
    }

    public String getText() {

        return text;
    }

    public Question(String text, String correct, String error1, String error2, String error3){
        this.text=text;
        this.correct=correct;
        this.error1=error1;
        this.error2=error2;
        this.error3=error3;

    }
}
