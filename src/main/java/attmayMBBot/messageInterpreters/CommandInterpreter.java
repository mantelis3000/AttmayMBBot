package attmayMBBot.messageInterpreters;

import attmayMBBot.commands.CommandsCommand;
import attmayMBBot.commands.ICommand;
import attmayMBBot.commands.NotFoundCommand;
import attmayMBBot.commands.arcadeCommand.ArcadeHighscoreCommand;
import attmayMBBot.commands.arcadeCommand.ArcadeXpCommand;
import attmayMBBot.commands.arcadeCommand.alineCommand.AlineCommand;
import attmayMBBot.commands.arcadeCommand.triviaCommand.TriviaCommand;
import attmayMBBot.commands.dinnerpostCommand.DinnerpostCommand;
import attmayMBBot.commands.goodnightCommand.GoodnightCommand;
import attmayMBBot.commands.jokeCommand.JokeCommand;
import attmayMBBot.commands.quoteCommand.*;
import attmayMBBot.commands.uwuCommand.UwUCommand;
import attmayMBBot.config.AttmayMBBotConfig;
import attmayMBBot.functionalities.arcade.ArcadeGameManager;
import attmayMBBot.functionalities.arcade.ArcadeManager;
import attmayMBBot.functionalities.quoteManagement.QuoteIDManager;
import attmayMBBot.functionalities.quoteManagement.QuoteManager;
import attmayMBBot.functionalities.quoteQuiz.QuoteQuizManager;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.Message;

import java.util.HashMap;

public class CommandInterpreter {
    private AttmayMBBotConfig config;
    private HashMap<String, ICommand> commandMap;
    private ICommand notFoundCommand;
    public CommandInterpreter(GatewayDiscordClient gateway, AttmayMBBotConfig config, QuoteManager quoteManager, ArcadeManager arcadeManager, QuoteIDManager quoteIDManager, QuoteQuizManager quoteQuizManager, ArcadeGameManager arcadeGameManager) {
        this.config = config;
        this.commandMap = new HashMap<String, ICommand>();
        this.commandMap.put("!goodnight", new GoodnightCommand(config));
        this.commandMap.put("!addquote", new AddQuoteCommand(config, quoteManager, quoteIDManager));
        this.commandMap.put("!quotelist", new AllQuotesCommand(config, quoteManager));
        this.commandMap.put("!quote", new RandomQuoteCommand(config, quoteManager));
        this.commandMap.put("!joke", new JokeCommand(config));
        this.commandMap.put("!dinnerpost", new DinnerpostCommand(config));
        this.commandMap.put("!uwu", new UwUCommand(config));
        this.commandMap.put("!addauthor", new AddQuoteAuthorCommand(config, quoteManager));
        this.commandMap.put("!addalias", new AddQuoteAuthorAliasCommand(config, quoteManager));
        this.commandMap.put("!quotequiz", new QuoteQuizCommand(config, quoteQuizManager));
        this.commandMap.put("!commands", new CommandsCommand());
        this.commandMap.put("!aline", new AlineCommand(config, arcadeGameManager));
        this.commandMap.put("!xp", new ArcadeXpCommand(config, arcadeManager));
        this.commandMap.put("!highscore", new ArcadeHighscoreCommand(gateway, config, arcadeManager));
        this.commandMap.put("!trivia", new TriviaCommand(config, arcadeGameManager));
        this.notFoundCommand = new NotFoundCommand();
    }
    public void interpretCommand(Message message){
        String messageContent = message.getContent();
        String[] args = messageContent.split(" ");
        //Execute the command with the most sexy of all methods: getOrDefault
        commandMap.getOrDefault(args[0].toLowerCase(), notFoundCommand).execute(message, args);
    }
}
