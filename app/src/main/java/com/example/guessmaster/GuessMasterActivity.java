package com.example.guessmaster;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * @Author Parker Rowe - 20122320
 * @Date    April 1st 2020
 * @Description Android app to play a fun guessing game. Some liberties taken where instructions make little sense or hurt flow of the game.
 */

public class GuessMasterActivity extends AppCompatActivity {
    private TextView entityName;
    private TextView ticketSum;
    private Button guessButton;
    private EditText userIn;
    private Button btnClearContext;
    private String userInput;
    private ImageView entityImage;
    String answer;

    private String input; // holds user input as a string
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    private int ticketAmnt = 0;
    private int currentEntityId;



    public int getTicketAmnt() {
        return ticketAmnt;
    }

    public void setTicketAmnt(int ticketAmnt) {
        this.ticketAmnt = ticketAmnt;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void setEntities(ArrayList<Entity> entities) {
        this.entities = entities;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity.clone());
    }

    private int genRandomEntityId() {
        Random rand = new Random();
        return rand.nextInt(entities.size());
    }

    // main play game method
    public void playGame(Entity entity) {
        answer = userIn.getText().toString();
        answer = answer.replace("\n", "").replace("\r","");

        try{
            Date date = new Date(answer);
            // GUESS
            if (answer.equalsIgnoreCase("quit")) {
                System.out.printf("\n\nFinishing with %d tickets\nQuitting Game...", ticketAmnt);
                System.exit(0);
            } else if (date.precedes(entity.getBorn())) {
                incorrectDialog("later");
            } else if (!date.equals(entity.getBorn())) {
                incorrectDialog("earlier");
            } else {
                // USER IS CORRECT, THIS BLOCK OF CODE BELOW IS RUN WHEN THE USER GUESSES CORRECTLY
                // new int tickets won calculates how many tickets the user won this round
                int ticketsWon = entity.getAwardedTicketNumber();
                // increment total amount of tickets the user has
                ticketAmnt += ticketsWon;
                ticketSum.setText("Tickets: " + ticketAmnt);

                // display dialog to inform user they are correct
                correctDialog(entity);

                // remove that entity from the list so it doesnt show up again
                entities.remove(entity);

                //clear user guess
                userIn.setText("");
            }

        } catch (NumberFormatException e){
            Toast.makeText(getBaseContext(), "Please enter a valid date", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getBaseContext(), "Error: please try again", Toast.LENGTH_SHORT).show();
        }
    }


    public void playGame() {
        playGame(entities.get(currentEntityId));
    }

    // custom prepareGame method which will prepare a new random entity and update the GUI elements
    public void prepareGame() {
        currentEntityId = genRandomEntityId();
        entityName.setText(entities.get(currentEntityId).getName());
        imageSetter();
        // display welcome message
        welcomeToGame(entities.get(currentEntityId));
    }

    // changeEntity method outlined in the assignment description is simplified to just prepare the game again
    public void changeEntity() {
        prepareGame();
    }

    public void imageSetter(){
        Entity currentEntity = entities.get(currentEntityId);

        // if else block to decide which image to display based on the current entity's name
        if(currentEntity.getName().equals("Justin Trudeau")) entityImage.setImageResource(R.drawable.justint);
        else if(currentEntity.getName().equals("Celine Dion")) entityImage.setImageResource(R.drawable.celidion);
        else if(currentEntity.getName().equals("myCreator")) entityImage.setImageResource(R.drawable.creator);
        else if(currentEntity.getName().equals("United States")) entityImage.setImageResource(R.drawable.usaflag);
        else entityImage.setImageResource(R.drawable.noimg);
    }

    public void welcomeToGame(final Entity entity){
        //welcome alert
        AlertDialog.Builder welcomeAlert = new AlertDialog.Builder(GuessMasterActivity.this);
        welcomeAlert.setTitle("GuessMaster_Game_v3");
        welcomeAlert.setMessage(entity.welcomeMessage());
        welcomeAlert.setCancelable(false);

        welcomeAlert.setNegativeButton("START GAME", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Game is Starting . . . Enjoy", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = welcomeAlert.create();
        dialog.show();
    }

    // parameter tip allows us to re-use the incorrect alert dialog, just telling user earlier/later
    public void incorrectDialog(String tip){
        AlertDialog.Builder incorrectAlert = new AlertDialog.Builder(GuessMasterActivity.this);
        incorrectAlert.setTitle("INCORRECT");
        incorrectAlert.setMessage("Try a " + tip + " date");
        incorrectAlert.setCancelable(false);

        incorrectAlert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = incorrectAlert.create();
        dialog.show();
    }

    public void correctDialog(final Entity entity){
        AlertDialog.Builder correctAlert = new AlertDialog.Builder(GuessMasterActivity.this);
        correctAlert.setTitle("CORRECT!");
        correctAlert.setMessage(entity.closingMessage());
        correctAlert.setCancelable(false);

        correctAlert.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Awarded " + entity.getAwardedTicketNumber() + " tickets", Toast.LENGTH_SHORT).show();
                if(entities.size() > 0){
                    //if there are entities left to guess, prepare the next one
                    prepareGame();
                } else {
                    // otherwise, they finished the game. Present them the final win dialog
                    winDialog();
                }
            }
        });
        AlertDialog dialog = correctAlert.create();
        dialog.show();
    }

    public void winDialog(){
        AlertDialog.Builder winAlert = new AlertDialog.Builder(GuessMasterActivity.this);
        winAlert.setTitle("YOU WON!");
        winAlert.setMessage("You guessed all of the entities correctly,\n finishing with " + ticketAmnt + " tickets.");
        winAlert.setCancelable(false);

        winAlert.setNegativeButton("Close game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        AlertDialog dialog = winAlert.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_master);

        //Specify the buttons in the view
        guessButton = (Button) findViewById(R.id.btnGuess);
        btnClearContext = (Button) findViewById(R.id.btnClear);
        //EditText for user input
        userIn = (EditText) findViewById(R.id.guessInput);
        //TextView objects in the view
        ticketSum = (TextView) findViewById(R.id.ticket);
        ticketSum.setText("Tickets: " + ticketAmnt);
        entityName = (TextView) findViewById(R.id.entityName);
        //imageview objects in the view
        entityImage = (ImageView) findViewById(R.id.entityImage);

        Politician trudeau = new Politician("Justin Trudeau", new Date("December", 25, 1971), "Male", "Liberal", 0.25);
        Singer dion = new Singer("Celine Dion", new Date("March", 30, 1961), "Female", "La voix du bon Dieu",
                new Date("November", 6, 1981), 0.5);
        Person myCreator = new Person("myCreator", new Date("May", 26, 2000), "Male", 1.0);
        Country usa = new Country("United States", new Date("July", 4, 1776), "Washinton D.C.", 0.1);
        addEntity(trudeau);
        addEntity(dion);
        addEntity(myCreator);
        addEntity(usa);


        // Onclick listener for the clear button
        btnClearContext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Display short message
                Toast.makeText(getApplicationContext(), "Changed entity", Toast.LENGTH_SHORT).show();
                // call changeEntity method to change the entity-to-guess on screen
                changeEntity();
            }
        });

        // Onclick listener for the guess button
        guessButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playGame();
            }
        });

        //call prepare game method to set up entity and get ready to guess
        prepareGame();


    }
}
