package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyStepDefinitions {
    @Given("Preconditions")
    public void preconditions() {

        System.out.println("Preconditions");
    }

    @When("User interaction")
    public void userInteraction() {
        System.out.println("User interaction");
    }

    @And("Condition one")
    public void conditionOne() {
        System.out.println("conditionOne");
    }

    @And("Condition two")
    public void conditionTwo() {
        System.out.println("conditionTwo");
    }

    @Then("Resul output")
    public void resulOutput() {
        System.out.println("resulOutput");
    }
}
