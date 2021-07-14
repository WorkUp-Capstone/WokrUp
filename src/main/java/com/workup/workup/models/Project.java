package com.workup.workup.models;

//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project{

    // TABLE CREATION AND COLUMNS

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 200)
//    @FullTextField
    private String title;

    @Column(nullable = false)
//    @FullTextField
    private String description;

    @Column (nullable = false)
    private Date creationDate;

    @Column (nullable = true)
    private Date completionDate;

    @Column (nullable = false)
    private String status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<ProjectImage> images;

    @ManyToMany(cascade = CascadeType.ALL)

    @JoinTable(
            name = "project_categories",
            joinColumns = {@JoinColumn(name="project_id")},
            inverseJoinColumns = {@JoinColumn(name="category_id")}
    )
    private List<Category> categories;

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User user;

    @OneToOne
    private User developerUser = null;

    // CONSTRUCTORS

    // Empty constructor for Spring to work
    public Project(){}

    // Read constructor includes Id

    public Project(long id, String title, String description, User user, User developerUser, List<ProjectImage> images, List<Category> categories, String status, java.sql.Date creationDate,java.sql.Date completedDate){
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.developerUser = developerUser;
        this.images = images;
        this.categories = categories;
        this.status = status;
        this.completionDate = completedDate;
        this.creationDate = creationDate;
    }

    // Insert constructor no Id needed

    public Project(String title, String description, User user, List<ProjectImage> images, List<Category> categories, String status, java.sql.Date creationDate){
        this.title = title;
        this.description = description;
        this.user = user;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getDeveloperUser() {
        return developerUser;
    }

    public void setDeveloperUser(User developerUser) {
        this.developerUser = developerUser;
    }

    public void resetDeveloperUser() {
        this.developerUser = null;
    }

    //    // MAYBE NEEDED FOR DATE CREATION AND COMPLETION WILL NEED TESTING DONE!!!!
    @Lob
    @Column(columnDefinition = "BLOB")
    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Lob
    @Column(columnDefinition = "BLOB")
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
