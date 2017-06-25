package com.skype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.social.borframework.service.ActivityProcessorCallback;
import org.springframework.social.borframework.service.BotService;
import org.springframework.social.botframework.api.data.Activity;
import org.springframework.social.botframework.api.data.MediaUrl;
import org.springframework.social.botframework.api.data.ThumbnailUrl;
import org.springframework.social.botframework.api.data.cards.AdaptiveCard;
import org.springframework.social.botframework.api.data.cards.AnimationCard;
import org.springframework.social.botframework.api.data.cards.AudioCard;
import org.springframework.social.botframework.api.data.cards.CardAction;
import org.springframework.social.botframework.api.data.cards.CardImage;
import org.springframework.social.botframework.api.data.cards.Fact;
import org.springframework.social.botframework.api.data.cards.HeroCard;
import org.springframework.social.botframework.api.data.cards.ReceiptCard;
import org.springframework.social.botframework.api.data.cards.ReceiptItem;
import org.springframework.social.botframework.api.data.cards.SignInCard;
import org.springframework.social.botframework.api.data.cards.ThumbnailCard;
import org.springframework.social.botframework.api.data.cards.VideoCard;
import org.springframework.social.botframework.api.data.cards.adaptiveCard.data.elements.Choice;
import org.springframework.social.botframework.api.data.cards.adaptiveCard.data.elements.InputChoiceSet;
import org.springframework.social.botframework.api.data.cards.adaptiveCard.data.elements.InputDate;
import org.springframework.social.botframework.api.data.cards.adaptiveCard.data.elements.InputText;
import org.springframework.social.botframework.api.data.cards.adaptiveCard.data.elements.InputToggle;
import org.springframework.social.botframework.api.data.cards.adaptiveCard.data.elements.TextBlock;
import org.springframework.social.botframework.api.data.cards.adaptiveCard.dict.CardElementType;
import org.springframework.social.botframework.api.data.cards.adaptiveCard.dict.ChoiceInputStyle;
import org.springframework.social.botframework.api.data.cards.adaptiveCard.dict.Color;
import org.springframework.social.botframework.api.data.cards.adaptiveCard.dict.SeparationStyle;
import org.springframework.social.botframework.api.data.cards.adaptiveCard.dict.TextInputStyle;
import org.springframework.social.botframework.api.dict.AttachmentLayout;
import org.springframework.social.botframework.api.dict.CardActionType;
import org.springframework.social.botframework.api.dict.TextFormat;
import org.springframework.social.botframework.text.builder.TextBuilder;
import org.springframework.social.botframework.text.data.Text;
import org.springframework.social.botframework.text.dict.Smiles;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anton Leliuk
 */
@RestController
public class EchoController {

    @Autowired
    private BotService botService;

    @RequestMapping(value = "/signIn")
    public void signIn(){}

    @RequestMapping(value = "/bf-chat", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void botFrameworkChat(@RequestBody Activity from){
        botService.process(from, new ActivityProcessorCallback() {
            @Override
            public void onPersonalMessage(Activity activity) {
                Activity replyActivity = null;
                switch (activity.getText()) {
                    case "message":
                        replyActivity = activity.createReplay()
                                .message()
                                .text(TextBuilder.newInstance().addPart(Text.text("some text")).addPart(Text.text(" ")).addPart(Smiles.Coffee).text())
                                .textFormat(TextFormat.xml);

                        break;
                    case "heroCard":
                        replyActivity = activity.createReplay()
                            .message()
                            .attachmentLayout(AttachmentLayout.carousel)
                            .text("Simple card")
                            .summary("Summary of the card")
                            .textFormat(TextFormat.markdown);
                        HeroCard hc = new HeroCard()
                            .title("Hotel Radisson Blu Hotel at Disneyland (r) Paris.")
                            .subTitle("Booked in the last 2 hours")
                            .text("Disneyland paris. 40 Aliee De la Mare dian Houleuse, Magny-le-Hongre, Seine-Marne.");
                        CardImage ci = new CardImage()
                            .url("https://dreamix.eu/blog/wp-content/uploads/2017/06/The-evolution-of-scalable-microservices-1508x706_c.jpg")
                            .alt("hello thumb")
                            .tap(new CardAction()
                                    .type(CardActionType.openUrl)
                                    .value("https://en.wikipedia.org/wiki/Space_Needle")
                                    .title("open"));
                        hc.image(ci);

                        hc.button(new CardAction().title("Select").value("select:102").type(CardActionType.imBack));

                        replyActivity.addAttachment(hc.toAttachment());
                        break;
                    case "signinCard":
                        replyActivity = activity.createReplay()
                            .message()
                            .attachmentLayout(AttachmentLayout.carousel)
                            .text("Simple signIn card")
                            .summary("Summary of the signIn card")
                            .textFormat(TextFormat.xml);

                        SignInCard sc = new SignInCard().title("Sign title").text("Some text").subTitle("Some subtitle");

                        CardAction signInButton = new CardAction()
                            .title("Login in TheSystem")
                            .type(CardActionType.signin)
                            .value("https://profitsoft.ua/uathesystem");
                        sc.button(signInButton);
                        replyActivity.addAttachment(sc.toAttachment());
                        break;
                    case "thumbnailCard":
                        replyActivity = activity.createReplay()
                            .message()
                            .attachmentLayout(AttachmentLayout.carousel)
                            .text("Simple thumbnail card")
                            .summary("Summary of the thumbnail card")
                            .textFormat(TextFormat.xml);

                        ThumbnailCard tc = new ThumbnailCard().title("Title").text("Text").subTitle("Sub title");

                        CardImage tcImage = new CardImage()
                                .alt("Thumbnail image")
                                .url("https://dreamix.eu/blog/wp-content/uploads/2017/06/The-evolution-of-scalable-microservices-1508x706_c.jpg")
                                .tap(new CardAction().value("https://dreamix.eu/blog/wp-content/uploads/2017/06/The-evolution-of-scalable-microservices-1508x706_c.jpg").type(CardActionType.openUrl));
                        tc.image(tcImage);

                        replyActivity.addAttachment(tc.toAttachment());
                        break;
                    case "receiptCard":
                            replyActivity = activity.createReplay()
                                    .attachmentLayout(AttachmentLayout.carousel)
                                    .message()
                                    .text("Simple receipt card")
                                    .summary("Summary of the receipt card")
                                    .textFormat(TextFormat.xml);

                            ReceiptCard rc = new ReceiptCard().title("Title").text("Text").subTitle("Sub title");

                            rc.fact(new Fact().key("Some key").value("Some value"));

                            ReceiptItem ri = new ReceiptItem();
                            CardImage rci = new CardImage()
                                .url("https://dreamix.eu/blog/wp-content/uploads/2017/06/The-evolution-of-scalable-microservices-1508x706_c.jpg")
                                .alt("alt text");
                            ri.image(rci).quantity(1).price("100 uah");
                            rc.item(ri).tax("TAX: 150 uah").vat("VAT: 200 uah").total("TOTAL: 350 uah");

                        replyActivity.addAttachment(rc.toAttachment());
                        break;
                    case "videoCard":
                        replyActivity = activity.createReplay()
                                .message()
                                .text("Video")
                                .summary("Some card with video");
                        VideoCard vc = new VideoCard()
                                .title("Big Buck Bunny")
                                .subTitle("by the Blender Institute")
                                .autoStart(false)
                                .text("Big Buck Bunny (code-named Peach) is a short computer-animated comedy film by the Blender Institute, part of the Blender Foundation. Like the foundation\\'s previous film Elephants Dream, the film was made using Blender, a free software application for animation made by the same foundation. It was released as an open-source film under Creative Commons License Attribution 3.0.")
                                .image(new ThumbnailUrl().url("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/Big_buck_bunny_poster_big.jpg/220px-Big_buck_bunny_poster_big.jpg").alt("alt text"))
                                .media(new MediaUrl().url("http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4").profile("Media profile"))
                                .button(new CardAction().type(CardActionType.openUrl).value("https://peach.blender.org/").title("Learn More"));
                        replyActivity.addAttachment(vc.toAttachment());
                        break;
                    case "audioCard":
                        replyActivity = activity.createReplay()
                                .message()
                                .text("Audio")
                                .summary("Some card with audio");
                        AudioCard ac = new AudioCard()
                                .title("I am your father")
                                .subTitle("Star Wars: Episode V - The Empire Strikes Back")
                                .text("The Empire Strikes Back (also known as Star Wars: Episode V – The Empire Strikes Back) is a 1980 American epic space opera film directed by Irvin Kershner. Leigh Brackett and Lawrence Kasdan wrote the screenplay, with George Lucas writing the film\\'s story and serving as executive producer. The second installment in the original Star Wars trilogy, it was produced by Gary Kurtz for Lucasfilm Ltd. and stars Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams, Anthony Daniels, David Prowse, Kenny Baker, Peter Mayhew and Frank Oz.")
                                .autoStart(false)
                                .image(new ThumbnailUrl().url("https://upload.wikimedia.org/wikipedia/en/3/3c/SW_-_Empire_Strikes_Back.jpg").alt("alt text"))
                                .media(new MediaUrl().url("http://www.wavlist.com/movies/004/father.wav"))
                                .button(new CardAction().type(CardActionType.openUrl).value("https://en.wikipedia.org/wiki/The_Empire_Strikes_Back").title("Read More"));
                        replyActivity.addAttachment(ac.toAttachment());
                        break;
                    case "animationCard":
                        replyActivity = activity.createReplay()
                                .message()
                                .text("Animation")
                                .summary("Some card with animation");
                        AnimationCard anc = new AnimationCard()
                                .title("Animation card")
                                .subTitle("Some sub title")
                                .image(new ThumbnailUrl().alt("alt text").url("https://docs.microsoft.com/en-us/bot-framework/media/how-it-works/architecture-resize.png"))
                                .media(new MediaUrl().url("http://i.giphy.com/Ki55RUbOV5njy.gif").profile("Cool animation"));
                        replyActivity.addAttachment(anc.toAttachment());
                        break;
                    case "adaptiveCard":
                        replyActivity = activity.createReplay()
                                .message()
                                .text("Adaptive card")
                                .summary("All adaptive cards");
                        AdaptiveCard adc = new AdaptiveCard()
                                .text("Card test")
                                .subTitle("Test adaptive card")
                                .type(CardElementType.AdaptiveCard)
                                .fallbackText("Client doesn't support adaptive cards.")
                                .body(new TextBlock().text("Warning block").color(Color.WARNING))
                                .body(new TextBlock().text("Attention block").color(Color.ATTENTION))
                                .body(new InputText().multiLine(false).maxLength(100).placeholder("Email").style(TextInputStyle.email))
                                .body(new InputText().multiLine(true).placeholder("Description").style(TextInputStyle.text))
                                .body(new InputToggle().title("Choice title").placeholder("Choice").value(true))
                                .body(new InputChoiceSet().style(ChoiceInputStyle.expanded)
                                        .choice(new Choice().title("less3").value("less3").selected(true))
                                        .choice(new Choice().title("more3").value("more3")))
                                .body(new InputDate().placeholder("Birthday"));
                        replyActivity.addAttachment(adc.toAttachment());
                        break;
                    default:
                        replyActivity = activity.createReplay().message().text("Available commands: message, heroCard, signinCard, thumbnailCard, receiptCard, videoCard, audioCard, animationCard, adaptiveCard.");
                        break;
                }

                botService.reply(replyActivity);
            }

            @Override
            public void onConversationUpdate(Activity activity) {
                activity.getMembersAdded().forEach(m -> {
                    Activity welcome = activity.createReplay()
                            .message()
                            .textFormat(TextFormat.xml)
                            .text("Hello " + m.getName() + ". " +
                                    "Available commands: message, heroCard, signinCard, thumbnailCard, receiptCard, videoCard, audioCard, animationCard, adaptiveCard.");
                    botService.reply(welcome);
                });
            }
        });
    }
}
