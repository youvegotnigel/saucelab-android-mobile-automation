package com.saucelabs.mobile.ui.stepdefs;

import com.saucelabs.mobile.ui.base.TestBase;
import io.cucumber.java.en.And;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class BasePageStepDefinitions extends TestBase {

    public static final Logger log = LogManager.getLogger(BasePageStepDefinitions.class.getName());

    @And("^I wait for \"(.+)\" seconds$")
    public void wait_time(String time) {
        implicitWait(Integer.parseInt(time));
    }

    @And("I click on {string} button")
    public void click_on_button(String text) {

        if(text.matches(".*\\[[\\d.]]")){
            var valueAndIndex = getValueAndIndex(text);
            //pageBase.clickOnButtonByName(valueAndIndex[0], valueAndIndex[1]);
        }else {
            //pageBase.clickOnButtonByName(text);
        }
    }



    @And("I should see the text {string} displayed")
    public void text_is_displayed(String text) {

        if(text.matches(".*\\[[\\d.]]")){
            var valueAndIndex = getValueAndIndex(text);
            //Assert.assertTrue(pageBase.isDisplayedInNormalizeSpace(valueAndIndex[0], valueAndIndex[1]),"Not found text ::: "+ text);
        }else {
            //Assert.assertTrue(pageBase.isDisplayedInNormalizeSpace(text),"Not found text ::: "+ text);
        }
    }

    @And("I should not see the text {string} displayed")
    public void text_is_not_displayed(String text) {

        if(text.matches(".*\\[[\\d.]]")){
            var valueAndIndex = getValueAndIndex(text);
            //Assert.assertFalse(pageBase.isDisplayedInNormalizeSpace(valueAndIndex[0], valueAndIndex[1]),"Found text ::: "+ text);
        }else {
            //Assert.assertFalse(pageBase.isDisplayedInNormalizeSpace(text),"Found text ::: "+ text);
        }
    }

    @And("^I set value \"(.+)\" for \"(.+)\"$")
    public void set_text_for_label(String answer, String question) {

        if(question.matches(".*\\[[\\d.]]")){
            var valueAndIndex = getValueAndIndex(question);
            //pageBase.setTextInputForLabel(valueAndIndex[0], valueAndIndex[1], answer);
        }else {
            //pageBase.setTextInputForLabel(question, answer);
        }
    }

    @And("^I tap on \"(.+)\" button$")
    public void tab_button(String text) {
        clickByAccessibilityId(text);
    }

    @And("Wait for {int} seconds")
    public void sleep(int n){
        delay(n);
    }

    @And("^I should see the text (?:|in|on|next) (?:|button|link|label|record) \"(.+)\" displayed$")
    public void text_should_be_displayed(String text){
        Assert.assertTrue(isDisplayedByAccessibilityId(text));
    }


}
