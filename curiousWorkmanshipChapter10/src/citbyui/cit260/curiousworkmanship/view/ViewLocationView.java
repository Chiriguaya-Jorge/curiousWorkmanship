/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package citbyui.cit260.curiousworkmanship.view;

import citbyui.cit260.curiousworkmanship.control.MapControl;
import citbyui.cit260.curiousworkmanship.enums.Actor;
import citbyui.cit260.curiousworkmanship.exceptions.ViewException;
import citbyui.cit260.curiousworkmanship.model.Location;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author jacksonrkj
 */
public class ViewLocationView extends View {
   
    public ViewLocationView() {
        super("\n"
            + "Enter the row and column number of the location you want to view (e.g., 1 3).");
    }


    @Override
    public boolean doAction(Object obj) {
        String choice = (String) obj;
        try{
            Point coordinates = this.getCoordinates(choice); // get the row and column
            if (coordinates == null)
                return true;
            
            // get the location in the map
            Location location = MapControl.getLocation(coordinates);
            
            // display contents of location
            this.displayLocationInfo(coordinates, location);

        } catch (ViewException ex) {
                this.console.println(ex.getMessage());
                return false;
        }       

        return true;  
        
    }
    
    public Point getCoordinates(String value) throws ViewException {
        Point coordinates = null;
        
        value = value.trim().toUpperCase();
        if (value.equals("Q"))
            return null;

        //tokenize values int string
        String[] values = value.split(" ");

        if (values.length < 2) {
            ErrorView.display("ViewLocationView", "You must enter both a row and column number.");
        }

        // parse out row and column numbers
        try {
            int row = Integer.parseInt(values[0]);
            int column = Integer.parseInt(values[1]);
            coordinates = new Point(row, column);

        } catch (NumberFormatException nf) {
            ErrorView.display("ViewLocationView", "The row or column entered is not a number.");
        }  
        
        return coordinates;
    }

    private void displayLocationInfo(Point coordinates, Location location) {
        if (coordinates == null || location == null) {
            ErrorView.display("ViewLocationView", 
                    "diplayLocationInfo - coordinates and/or location is null");
            return;
        }
        
        if (location.getScene() == null) {
            this.console.println("\nThis location is empty");
            return;
        }
        
        String message = "\nLocation (" + coordinates.x + ", " + coordinates.y + ")\n"
                         + this.getBlockedMessage(location.getScene().getDescription());
        this.console.println(message);
   
        ArrayList<Actor> actorsInLocation = location.getActors();
        if (actorsInLocation.size() < 1) {
            this.console.println("\nThere are no actors in this location");
        }
        else {
            this.console.println("\nThe following actors are currently in this location");
            for (Actor actor : actorsInLocation) {
                this.console.println("   " + actor);        
            }
        }
        
    }
    
}
