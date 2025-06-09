package net.etf.project.model.vehicles;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Represents a malfunction that occurs in a vehicle.
 * Each malfunction has a description and a date and time when it occurred.
 *
 * @author Jovana Šikanjić
 * @version 1.0
 */
public class Malfunction implements Serializable
{
    /** The description of the malfunction. */
    private String description;

    /** The date and time when the malfunction occurred. */
    private Date malfunctionDateTime;

    /**
     * Constructs a new Malfunction with the given date and time.
     * A random description is generated from a set of predefined malfunction descriptions.
     *
     * @param malfunctionDateTime The date and time when the malfunction occurred.
     */
    public Malfunction(Date malfunctionDateTime)
    {
        this.description = generateRandomDescription();
        this.malfunctionDateTime = malfunctionDateTime;
    }

    /**
     * Generates a random malfunction description from a list of possible descriptions.
     *
     * @return A random malfunction description.
     */
    private String generateRandomDescription()
    {
        String[] possibleDescriptions = {"Tire puncture","Electrical issue", "Battery malfunction","Brake failure", "Engine overheating", "Headlight failure"};
        Random random = new Random();
        return possibleDescriptions[random.nextInt(possibleDescriptions.length)];
    }

    /**
     * Returns the description of the malfunction.
     *
     * @return The description of the malfunction.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the malfunction.
     *
     * @param description The new description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the date and time when the malfunction occurred.
     *
     * @return The malfunction date and time.
     */
    public Date getMalfunctionDateTime() {
        return malfunctionDateTime;
    }

    /**
     * Sets the date and time when the malfunction occurred.
     *
     * @param malfunctionDateTime The new malfunction date and time to set.
     */
    public void setMalfunctionDateTime(Date malfunctionDateTime) {
        this.malfunctionDateTime = malfunctionDateTime;
    }
}