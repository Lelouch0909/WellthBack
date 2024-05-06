package com.lontsi.wellthappback.services.impl;

import com.lontsi.wellthappback.dto.AlimentProposeDto;
import com.lontsi.wellthappback.dto.AlimentationDto;
import com.lontsi.wellthappback.dto.RegimeDto;
import com.lontsi.wellthappback.dto.UtilisateurDto;
import com.lontsi.wellthappback.exceptions.UtilisateurException;
import com.lontsi.wellthappback.models.RegimeObjectif;
import com.lontsi.wellthappback.models.typeRepas;
import com.lontsi.wellthappback.services.AiService;
import com.lontsi.wellthappback.services.UtilisateurService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AiServiceImpl implements AiService {

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    private String apiKey4Chat;

    @Autowired
    private UtilisateurService utilisateurService;


    public static void main(String[] args) throws UtilisateurException {
        UtilisateurDto utilisateurDto = new UtilisateurDto();
        RegimeDto regimeDto = new RegimeDto();

        utilisateurDto.setAge(21);
        utilisateurDto.setSexe("homme");
        utilisateurDto.setTaille(1.8F);
        utilisateurDto.setPoids(60F);
        utilisateurDto.setAntecedentsMedicaux("maux d estomac, diabete, carrie dentaire");

        regimeDto.setDureeEnJours(30);
        regimeDto.setRegimeObjectif(RegimeObjectif.PERTE_DE_POIDS);
        regimeDto.setObjectif(-8F);
        regimeDto.setAlimentationHabituelle("ndole, riz parfume");


        AiServiceImpl ai = new AiServiceImpl();
        RegimeDto regime = ai.createRegime(utilisateurDto, regimeDto);
        AlimentationDto alimentationDto = ai.createAlimentation(regime,utilisateurDto);
        AlimentProposeDto alimentProposeDto = ai.createAlimentPropose(alimentationDto.getAlimentsPropose().get(2),alimentationDto,regimeDto,utilisateurDto );

        JSONObject alimentation = new JSONObject(alimentProposeDto);
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println(alimentation.toString());
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------------------------------");

    }


    // Prompt Token ~750
    // Response Token ~700
    // Total Token ~1500

    @Override
    public RegimeDto createRegime(UtilisateurDto reqUser, RegimeDto regimeDto) {
        System.out.println("-------------------1--------------------");


        OpenAiApi openAiApi = new OpenAiApi(apiKey);

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel("gpt-3.5-turbo-16k")
                .withTemperature(0.5F)
                .withTopP(1F)
                .build();

        OpenAiChatClient openAiChatClient = new OpenAiChatClient(openAiApi, options);
        System.out.println("-----------------2----------------------");

        SystemMessage systemMessage = new SystemMessage("""
                    Tu es expert nutrioniste et coach sportif avec les informations que t enverra 
                    l utilisateur sous forme cle valeur etablit un regime, cree les objets Regime ; Alimentation
                     et  renvois sous format json un objet avec les champs suivant:
                     -Regime < L objet Regime qui lui meme contient les champs :
                            -nomRegime <un nom motivant et inventif pour ce regime>,
                            -calorieQuotidien < de type float; une approximation du nombre de calorie avec beaucoup d optimisme qu il perdra ou
                            gagnera en fonction de son objectif et de la duree de son regime>,
                             >,
                     -Alimentation < L objet Alimentation qui represente ce qu il consomme au jour j=1 compte tenu de son regime
                     et de son objectif tu dois bien faire attention de prendre en compte les aliments a eviter et les antecedents medicaux
                      de l utilisateur avant de faire une Alimentation ou de propose un AlimentPropose. il contient les champs:
                                   -nomAlimentation <un nom inventif qui represente la consommation du jour>,
                                   -nutriments <Represente les nutriments que contiennent les plats du champ AlimentsProposes>,
                                   -arg < de type string ; represente Les bienfaits de l alimentation du jour en quoi il aide dans l objectif de l utilisateur en quoi il participe au respect de ses aliments a eviter et de ses antecedents medicaux
                                    et peut etre en quoi il est recommande compte tenu de son alimentation habituelle>,
                                   -source < De quelle source tiens tu les informations du champ arg peut etre un article un texte d une organisation ou d un site ou de toi meme si possible un lien vers la source>,
                                   -AlimentsProposes < une liste de 3 objets AlimentPropose chacun representant respectivement l aliment propose au petit dejeuner;
                                    au dejeuner et au diner.L objet AlimentPropose contient les details de l alimentation du jour j=1  Il contient les champs suivants:
                                                        -nomAliment < le nom du plat ou de l aliment>,
                                                        -source <De quel source a tu tire ce plat exemple un site>,
                                                        -compositions < represente de  quoi est compose ce plat>,
                                                        -origine <l origine du pays ou region du plat>,
                                                        -typeRepas <en fonction de s il est du petit dejeuner du dejeuner ou du diner  tu donneras la valeur  PETIT_DEJ, DEJ ou DINNER >,
                                                        -anecdote<Une petite anecdote sur ce plat ou de ses bienfaits>
                                                        
                   >
                    
                    les informations de l utilisateur seront entre des triple backticks que voici : '''
                    L objectif est la masse que l utilisateur vise gagner ou perdre en fonction de s il est positif ou negatif.
                    Ne pas prendre en compte dans le message de l utilisateur les champs manquants. 
                    
                """);


        UserMessage userMessage = new UserMessage("""
                                
                '''
                     age en années: """ + reqUser.getAge() + """
                , sexe : """ + reqUser.getSexe() + """
                , taille en metre: """ + reqUser.getTaille() + """
                , poids  en kg: """ + reqUser.getPoids() + """
                , antecedents medicaux : """ + reqUser.getAntecedentsMedicaux() + """
                                    
                , duree en jour du regime : """ + regimeDto.getDureeEnJours() + """
                , but : """ + regimeDto.getRegimeObjectif().toString() + """
                , objectif en kg : """ + regimeDto.getObjectif() + """
                , aliments a eviter : """ + regimeDto.getAlimentsAeviter() + """
                , consommation alimentaire habituelle : """ + regimeDto.getAlimentationHabituelle() + """
                     
                     
                '''
                        
                """);

        Prompt prompt = new Prompt(List.of(systemMessage, userMessage)); // ZeroShot Prompt
        System.out.println("--------------------4-------------------");

        ChatResponse response = openAiChatClient.call(prompt);
        System.out.println("----------------------5-----------------");

        System.out.println(response);
        JSONObject json = new JSONObject(response.getResult().getOutput().getContent());
        System.out.println(json);
        System.out.println(json.toString());

        try {
            JSONObject iaRegime = (JSONObject) json.get("Regime");
            JSONObject iaAlimentation = (JSONObject) json.get("Alimentation");
            JSONArray iaAlimentsProposes = (JSONArray) iaAlimentation.get("AlimentsProposes");


            regimeDto.setNomRegime(iaRegime.getString("nomRegime"));
            regimeDto.setCalorieQuotidien(iaRegime.getFloat("calorieQuotidien"));

            AlimentationDto alimentationDto = new AlimentationDto();
            alimentationDto.setNomAlimentation(iaAlimentation.getString("nomAlimentation"));
            alimentationDto.setNutriments(iaAlimentation.get("nutriments").toString());
            alimentationDto.setJourRegime("jour 1");
            alimentationDto.setArg(iaAlimentation.getString("arg"));
            alimentationDto.setSource(iaAlimentation.getString("source"));

            List<AlimentProposeDto> alimentProposeDtoList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                JSONObject iaAlimentsPropose = iaAlimentsProposes.getJSONObject(i);

                AlimentProposeDto alimentProposeDto = new AlimentProposeDto();

                alimentProposeDto.setNomAliment(iaAlimentsPropose.getString("nomAliment"));
                alimentProposeDto.setSource(iaAlimentsPropose.getString("source"));
                alimentProposeDto.setCompositions(iaAlimentsPropose.getString("compositions"));
                alimentProposeDto.setOrigine(iaAlimentsPropose.getString("origine"));
                alimentProposeDto.setAnecdote(iaAlimentsPropose.getString("anecdote"));

                switch (iaAlimentsPropose.getString("typeRepas")) {
                    case "PETIT_DEJ":
                        alimentProposeDto.setTypeRepas(typeRepas.PETIT_DEJ);
                        break;
                    case "DEJ":
                        alimentProposeDto.setTypeRepas(typeRepas.DEJ);
                        break;
                    case "DINNER":
                        alimentProposeDto.setTypeRepas(typeRepas.DINNER);
                        break;
                }

                alimentProposeDtoList.add(alimentProposeDto);
                alimentationDto.setAlimentsPropose(alimentProposeDtoList);
                regimeDto.setAlimentations(List.of(alimentationDto));


            }
        } catch (Exception exception) {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Erreur dans la creation de l objet Regime par l'ia");
            System.out.println(exception.getMessage());
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");

        }
        System.out.println(regimeDto);

        return regimeDto;
    }

    @Override
    public AlimentationDto createAlimentation(RegimeDto regimeDto, UtilisateurDto reqUser) {
        OpenAiApi openAiApi = new OpenAiApi(apiKey);

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel("gpt-3.5-turbo-16k")
                .withTemperature(1F)
                .withTopP(1F)
                .build();

        OpenAiChatClient openAiChatClient = new OpenAiChatClient(openAiApi, options);
        SystemMessage systemMessage = new SystemMessage("""
                Tu es expert nutrioniste et coach sportif. Tu as deja etablit un regime mensuel pour cet utilisateur; avec les informations que t enverra 
                l utilisateur sous forme cle valeur; etablit une alimentation pour ce jour numero """ + 2
                + """
                      du regime. Cree l' objet Alimentation
                     et  renvois sous format json un objet avec les champs suivant:
                    
                     -Alimentation < L objet Alimentation qui represente ce qu il consomme au jour j=1 compte tenu de son regime
                     et de son objectif tu dois bien faire attention de prendre en compte les aliments a eviter et les antecedents medicaux
                      de l utilisateur avant de faire une Alimentation ou de propose un AlimentPropose. il contient les champs:
                                  
                                   -nomAlimentation <un nom inventif qui represente la consommation du jour>,
                                  
                                   -nutriments <Represente les nutriments que contiennent les plats du champ AlimentsProposes>,
                                   
                                   -arg <de type string ; Les bienfaits de l alimentation du jour en quoi il aide dans l objectif de l utilisateur en quoi il participe au respect de ses aliments a eviter et de ses antecedents medicaux
                                    et peut etre en quoi il est recommande compte tenu de son alimentation habituelle>,
                                   
                                   -source < De quelle source tiens tu les informations du champ arg peut etre un article un texte d une organisation ou d un site ou de toi meme si possible un lien vers la source>,
                                  
                                   -AlimentsProposes < une liste de 3 objets AlimentPropose chacun representant respectivement l aliment propose au petit dejeuner;
                                    au dejeuner et au diner.L objet AlimentPropose contient les details de l alimentation du jour j=1  Il contient les champs suivants:
                                                        -nomAliment < le nom du plat ou de l aliment>,
                                                        -source <De quel source a tu tire ce plat exemple un site>,
                                                        -compositions < represente de quoi est compose ce plat>,
                                                        -origine <l origine du pays ou region du plat>,
                                                        -typeRepas <en fonction de s il est du petit dejeuner du dejeuner ou du diner  tu donneras la valeur  PETIT_DEJ, DEJ ou DINNER >,
                                                        -anecdote<Une petite anecdote sur ce plat ou de ses bienfaits>
                                                        >
                   
                    
                    les informations de l utilisateur seront entre des triple backticks que voici : '''
                    L objectif est la masse que l utilisateur vise gagner ou perdre en fonction de s il est positif ou negatif.
                    Ne pas prendre en compte dans le message de l utilisateur les champs manquants. 
                    
                             
                """);


        AlimentationDto alimentationDto = new AlimentationDto();

        try {
            UserMessage userMessage = new UserMessage("""
                                    
                    '''
                         age en années: """ + reqUser.getAge() + """ 
                    , sexe : """ + reqUser.getSexe() + """
                    , taille en metre: """ + reqUser.getTaille() + """
                    , poids  en kg: """ + reqUser.getPoids() + """
                    , antecedents medicaux : """ + reqUser.getAntecedentsMedicaux() + """
                                          
                    , duree en jour du regime : """ + regimeDto.getDureeEnJours() + """
                    , but : """ + regimeDto.getRegimeObjectif().toString() + """
                    , objectif en kg : """ + regimeDto.getObjectif() + """
                    , aliments a eviter : """ + regimeDto.getAlimentsAeviter() + """
                    , consommation alimentaire habituelle : """ + regimeDto.getAlimentationHabituelle() + """
                    , calorie quotidienne a consomme ou a perdre : """ + regimeDto.getCalorieQuotidien() + """                    
                                          
                    '''
                            
                    """);


            Prompt prompt = new Prompt(List.of(systemMessage, userMessage)); // ZeroShot Prompt
            ChatResponse response = openAiChatClient.call(prompt);
            JSONObject json = new JSONObject(response.getResult().getOutput().getContent());


            JSONObject iaAlimentation = (JSONObject) json.get("Alimentation");
            JSONArray iaAlimentsProposes = (JSONArray) iaAlimentation.get("AlimentsProposes");


            alimentationDto.setNomAlimentation(iaAlimentation.getString("nomAlimentation"));
            alimentationDto.setNutriments(iaAlimentation.get("nutriments").toString());
            alimentationDto.setJourRegime("jour 1");
            alimentationDto.setArg(iaAlimentation.getString("arg"));
            alimentationDto.setSource(iaAlimentation.getString("source"));

            List<AlimentProposeDto> alimentProposeDtoList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                JSONObject iaAlimentsPropose = iaAlimentsProposes.getJSONObject(i);

                AlimentProposeDto alimentProposeDto = new AlimentProposeDto();

                alimentProposeDto.setNomAliment(iaAlimentsPropose.getString("nomAliment"));
                alimentProposeDto.setSource(iaAlimentsPropose.getString("source"));
                alimentProposeDto.setCompositions(iaAlimentsPropose.getString("compositions"));
                alimentProposeDto.setOrigine(iaAlimentsPropose.getString("origine"));
                alimentProposeDto.setAnecdote(iaAlimentsPropose.getString("anecdote"));

                switch (iaAlimentsPropose.getString("typeRepas")) {
                    case "PETIT_DEJ":
                        alimentProposeDto.setTypeRepas(typeRepas.PETIT_DEJ);
                        break;
                    case "DEJ":
                        alimentProposeDto.setTypeRepas(typeRepas.DEJ);
                        break;
                    case "DINNER":
                        alimentProposeDto.setTypeRepas(typeRepas.DINNER);
                        break;
                }

                alimentProposeDtoList.add(alimentProposeDto);
                alimentationDto.setAlimentsPropose(alimentProposeDtoList);

            }
        } catch (Exception e) {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Erreur dans la creation de l objet Alimentation par l'ia");
            System.out.println(e.getMessage());
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");

        }
        return alimentationDto;

    }


    @Override
    public AlimentProposeDto createAlimentPropose(AlimentProposeDto alimentProposeDto, AlimentationDto alimentationDto, RegimeDto regimeDto, UtilisateurDto reqUser) {
        OpenAiApi openAiApi = new OpenAiApi(apiKey);

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .withModel("gpt-3.5-turbo-16k")
                .withTemperature(1F)
                .withTopP(1F)
                .build();

        OpenAiChatClient openAiChatClient = new OpenAiChatClient(openAiApi, options);
        SystemMessage systemMessage = new SystemMessage("""
                Tu es expert nutrioniste et coach sportif. Tu as deja etablit un regime mensuel pour cet utilisateur; 
                Tu lui as propose un plat/aliment qu il souhaite changer;
                avec les informations que t enverra 
                l utilisateur sous forme cle valeur; etablit un nouvel AlimentPropose  pour ce jour numero """ + 2
                + """
                 du regime. Cree l' objet AlimentationPropose
                et  renvois sous format json un objet avec les champs suivant:
                                    
                -AlimentPropose <represente l' aliment propose au """ + alimentProposeDto.getTypeRepas() + """
                                              .
                                              L objet AlimentPropose contient les details de l alimentation du jour.  Il contient les champs suivants:
                                              -nomAliment < le nom du plat ou de l aliment>,
                                              -source <De quel source a tu tire ce plat exemple un site>,
                                              -compositions < represente de quoi est compose ce plat>,
                                              -origine <l origine du pays ou region du plat>,
                                              -typeRepas <en fonction de s il est du petit dejeuner du dejeuner ou du diner  tu donneras la valeur  PETIT_DEJ, DEJ ou DINNER >,
                                              -anecdote <Une petite anecdote sur ce plat ou de ses bienfaits>
                                                        >
                   
                    
                    les informations de l utilisateur seront entre des triple backticks que voici : '''
                    L objectif est la masse que l utilisateur vise gagner ou perdre en fonction de s il est positif ou negatif.
                    <aliment propose precedemment>  est l aliment que tu as precedemment propose et que l utilisateur souhaite change.
                    <autres aliments de la journee> represente les autres aliments qu il doit consommer ce jour; Le nouvel AlimentPropose couplé aux
                     <autres aliment de la journee> doivent respecte les exigences de la journee du regime.
                    Ne pas prendre en compte dans le message de l utilisateur les champs manquants. 
                    
                             
                """);

        String proposes = "";
        for (AlimentProposeDto alimentPropose : alimentationDto.getAlimentsPropose()) {
            proposes = proposes + " " + alimentPropose.getNomAliment();
        }


        AlimentProposeDto alimentProposeDto2 = new AlimentProposeDto();

        try {
            UserMessage userMessage = new UserMessage("""
                                    
                    '''
                         age en années: """ + reqUser.getAge() + """ 
                    , sexe : """ + reqUser.getSexe() + """
                    , taille en metre: """ + reqUser.getTaille() + """
                    , poids  en kg: """ + reqUser.getPoids() + """
                    , antecedents medicaux : """ + reqUser.getAntecedentsMedicaux() + """
                                          
                    , duree en jour du regime : """ + regimeDto.getDureeEnJours() + """
                    , but : """ + regimeDto.getRegimeObjectif().toString() + """
                    , objectif en kg : """ + regimeDto.getObjectif() + """
                    , aliments a eviter : """ + regimeDto.getAlimentsAeviter() + """
                    , consommation alimentaire habituelle : """ + regimeDto.getAlimentationHabituelle() + """
                    , calorie quotidienne a consomme ou a perdre : """ + regimeDto.getCalorieQuotidien() + """      
                                        
                    , aliment propose precedemment : """ + alimentProposeDto.getNomAliment() + """              
                    , autres aliments de la journee : """ + proposes
                    + """
                    '''
                            
                    """);


            Prompt prompt = new Prompt(List.of(systemMessage, userMessage)); // ZeroShot Prompt
            ChatResponse response = openAiChatClient.call(prompt);
            JSONObject json = new JSONObject(response.getResult().getOutput().getContent());


            JSONObject iaAlimentsPropose = (JSONObject) json.get("AlimentPropose");



            alimentProposeDto2.setNomAliment(iaAlimentsPropose.getString("nomAliment"));
            alimentProposeDto2.setSource(iaAlimentsPropose.getString("source"));
            alimentProposeDto2.setCompositions(iaAlimentsPropose.getString("compositions"));
            alimentProposeDto2.setOrigine(iaAlimentsPropose.getString("origine"));
            alimentProposeDto2.setAnecdote(iaAlimentsPropose.getString("anecdote"));

            switch (iaAlimentsPropose.getString("typeRepas")) {
                case "PETIT_DEJ":
                    alimentProposeDto2.setTypeRepas(typeRepas.PETIT_DEJ);
                    break;
                case "DEJ":
                    alimentProposeDto2.setTypeRepas(typeRepas.DEJ);
                    break;
                case "DINNER":
                    alimentProposeDto2.setTypeRepas(typeRepas.DINNER);
                    break;
            }


        } catch (
                Exception e) {
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Erreur dans la creation de l objet AlimentPropose par l'ia");
            System.out.println(e.getMessage());
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("--------------------------------------------------------------------------------------");

        }
        return alimentProposeDto2;

    }


    @Override
    public String askToAi(String message) {
        return null;
    }


}
