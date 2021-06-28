package com.workup.workup.models;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    // TABLE CREATION AND COLUMNS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column (nullable = false)
    private Date creationDate;

    @Column (nullable = true)
    private Date completionDate;

    @OneToOne
    private Status status;

    // PROJECT IMAGES MODEL NOT CREATED
    // DELETE COMMENTS ONCE IMAGES MODEL CREATED!!!
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<ProjectImage> images;

    // PROJECT CATEGORIES MODEL NOT CREATED
    // DELETE COMMENTS ONCE CATEGORIES MODEL CREATED!!!
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "categories",
            joinColumns = {@JoinColumn(name="project_id")},
            inverseJoinColumns = {@JoinColumn(name="category_id")}
    )
    private List<Category> categories;

    // USER MODEL NOT CREATED YET
    // DELETE COMMENTS ONCE USER MODEL CEREATED!!!!
    @OneToOne
    private User ownerUser;

    // SHOULD THIS BE A DIFFERENT RELATIONSHIP????
    @OneToOne
    private User developerUser;

    // CONSTRUCTORS

    // Empty constructor for Spring to work
    public Project(){}

    // Read constructor includes Id
    public Project(long id, String title, String description, User ownerUser, User developerUser, List<ProjectImage> images, List<Category> categories, Status status, java.sql.Date creationDate,java.sql.Date completedDate){
        this.id = id;
        this.title = title;
        this.description = description;
        this.ownerUser = ownerUser;
        this.developerUser = developerUser;
        this.images = images;
        this.categories = categories;
        this.status = status;
        this.completionDate = completedDate;
        this.creationDate = creationDate;
    }

    // Insert constructor no Id needed
    public Project(String title, String description, User ownerUser, List<ProjectImage> images, List<Category> categories, Status status, java.sql.Date creationDate){
        this.title = title;
        this.description = description;
        this.ownerUser = ownerUser;
        this.images = images;
        this.categories = categories;
        this.status = status;
        this.creationDate = creationDate;
    }


    //  GETTERS AND SETTERS


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ProjectImage> getImages() {
        return images;
    }

    public void setImages(List<ProjectImage> images) {
        this.images = images;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public User getDeveloperUser() {
        return developerUser;
    }

    public void setDeveloperUser(User developerUser) {
        this.developerUser = developerUser;
    }

    //    // MAYBE NEEDED FOR DATE CREATION AND COMPLETION WILL NEED TESTING DONE!!!!
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private java.sql.Date parseDate(String date) {
        try {
            return new Date(DATE_FORMAT.parse(date).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private java.sql.Timestamp parseTimestamp(String timestamp) {
        try {
            return new Timestamp(DATE_TIME_FORMAT.parse(timestamp).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
