# Commander
The commander class allows you to ask the user for a command in a simple way. It handles all the logic and returns a `CommandResult` object.
You need to create a `Commander` class (and provide the type of value that commands will have. Usually an `enum`) and specify the available commands by passing an array of `Command`.

## Ask the user for static commands
You can specify some static commands by defining them via a String. The user will be asked to write a command.
```java
var commander = new Commander<Commands>(new Command[] {
    new Command<>("say hello", Commands.SayHello),
    new Command<>("say bye", Commands.SayBye)
});

final CommandResult<Commands> command = commander.askForCommand();
```

If the user writes **say hello**, the value obtained when calling `command.getResult();` will be `Commands.SayHello`.

## Ask the user for dynamic commands
In case the user needs to provide some parameters inside the command you can define variables inside the command definition.

```java
var commander = new Commander<Commands>(new Command[] {
    new Command<>("say hello", Commands.SayHello),
    new Command<>("say bye", Commands.SayBye),
    new Command<>("say word :text", Commands.SayWord)
});

final CommandResult<Commands> command = commander.askForCommand();
```

Then, if the user types **say word something**, the value obtained when calling `command.getResult();` will be `Commands.SayWord`.
But this is not all, we can get the value the user provided using `command.getParameter("text");`.
For example, we can print the provided value this way:
```java
if(command.getResult() == Commands.SayWord) {
    System.out.println(command.getParameter("text"));
}
```