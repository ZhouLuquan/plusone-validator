package xyz.zhouxy.plusone.example.validator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import xyz.zhouxy.plusone.ExampleException;
import xyz.zhouxy.plusone.example.ExampleCommand;
import xyz.zhouxy.plusone.validator.BaseValidator;

class BaseValidatorTest {

    @Test
    void withRule_validInput() {
        ExampleCommand exampleCommand = new ExampleCommand();
        exampleCommand.setStringProperty("Foo");

        BaseValidator<ExampleCommand> validator = new BaseValidator<ExampleCommand>() {
            {
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"),
                        "The stringProperty must be equal to 'Foo'");
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"),
                        () -> ExampleException.withMessage("The stringProperty must be equal to 'Foo'"));
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"),
                        str -> ExampleException.withMessage("The stringProperty must be equal to 'Foo', but is was '%s'.", str));
                withRule(command -> {
                    final String stringProperty = command.getStringProperty();
                    if (!Objects.equals(stringProperty, "Foo")) {
                        throw ExampleException.withMessage("");
                    }
                });
            }
        };
        assertDoesNotThrow(() -> validator.validate(exampleCommand));
    }

    @Test
    void withRule_invalidInput() {
        ExampleCommand command = new ExampleCommand();

        BaseValidator<ExampleCommand> ruleWithMessage = new BaseValidator<ExampleCommand>() {
            {
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"),
                        "The stringProperty must be equal to 'Foo'");
            }
        };
        IllegalArgumentException eWithSpecifiedMessage = assertThrows(
                IllegalArgumentException.class, () -> ruleWithMessage.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo'", eWithSpecifiedMessage.getMessage());

        BaseValidator<ExampleCommand> ruleWithExceptionSupplier = new BaseValidator<ExampleCommand>() {
            {
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"),
                        () -> ExampleException.withMessage("The stringProperty must be equal to 'Foo'"));
            }
        };
        ExampleException specifiedException = assertThrows(
                ExampleException.class,
                () -> ruleWithExceptionSupplier.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo'", specifiedException.getMessage());

        BaseValidator<ExampleCommand> ruleWithExceptionFunction = new BaseValidator<ExampleCommand>() {
            {
                withRule(command -> Objects.equals(command.getStringProperty(), "Foo"), command -> ExampleException
                        .withMessage("The stringProperty must be equal to 'Foo', but is was '%s'.", command.getStringProperty()));
            }
        };
        ExampleException specifiedException2 = assertThrows(
                ExampleException.class,
                () -> ruleWithExceptionFunction.validate(command));
        assertEquals("The stringProperty must be equal to 'Foo', but is was 'null'.", specifiedException2.getMessage());

        BaseValidator<ExampleCommand> rule = new BaseValidator<ExampleCommand>() {
            {
                withRule(command -> {
                    final String stringProperty = command.getStringProperty();
                    if (!Objects.equals(stringProperty, "Foo")) {
                        throw ExampleException.withMessage("");
                    }
                });
            }
        };
        ExampleException e = assertThrows(ExampleException.class, () -> rule.validate(command));
        assertEquals("", e.getMessage());
    }
}
