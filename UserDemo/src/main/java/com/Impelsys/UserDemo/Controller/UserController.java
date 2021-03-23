package com.Impelsys.UserDemo.Controller;

import com.Impelsys.UserDemo.Exception.ErrorDetails;
import com.Impelsys.UserDemo.Exception.RecordNotFoundException;
import com.Impelsys.UserDemo.Model.User;
import com.Impelsys.UserDemo.Repository.UserRepository;
import com.Impelsys.UserDemo.Service.UserService;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/users") //creation of RESTful web services.
@CrossOrigin(origins = "http://localhost:4200")

public class UserController
{
    public static final Logger logger = LogManager.getLogger( UserController.class );

    @Autowired
    UserService service;


    //Mapping to get all users
    @GetMapping
    public ResponseEntity <List<User>> getAllUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDir)
    {
        logger.info( "Getting all the user data..." );
        logger.info( " The page number is : [" + pageNo + "]" );
        logger.info( " The page Size is : [" + pageSize + "]" );
        logger.info( " The data is sorted by : [" + sortBy + "]" );
        logger.info( " The sorting direction is is : [" + sortDir + "]" );

        List<User> list = service.getAllUsers(pageNo, pageSize, sortBy, sortDir);


        return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    //Searching through records using keywords
    @GetMapping(value = {"/search", "/search/{keyword}"})
    public ResponseEntity <List<User>> FilterUsers(     @RequestParam(defaultValue = "0") Integer pageNo,
                                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                                        @RequestParam(defaultValue = "id") String sortBy,
                                                        @RequestParam(defaultValue = "ASC") String sortDir,
                                                        @PathVariable( value = "keyword" , required = false)  String keyword)
    {
        logger.trace( " Searching using keyword..." );
        logger.info( " The page number is : [" + pageNo + "]" );
        logger.info( " The page Size is : [" + pageSize + "]" );
        logger.info( " The data is sorted by : [" + sortBy + "]" );
        logger.info( " The sorting direction is is : [" + sortDir + "]" );
        logger.info( " The keyword for searching is : [" + keyword + "]" );

        logger.info( " Searching all the users having [" + keyword + "] in the first name, last name, email id or phone number" );
        List<User> listUsers = service.listAll(pageNo, pageSize, sortBy, sortDir,keyword);

        return new ResponseEntity<List<User>>(listUsers, new HttpHeaders(), HttpStatus.OK);
    }

    //Display user data by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getEmployeeById(@PathVariable("id") Long id) throws RecordNotFoundException
    {
        logger.trace( "The Id that is used to search : [" + id + "]" );
        logger.info( " Searching user with Id : [" + id + "]" );

        User entity = service.getUserById(id);

        return new ResponseEntity<User>(entity, new HttpHeaders(), HttpStatus.OK);
    }

    //Get the total number of records
    @GetMapping("/count")
    public long totalNumberOfRecords()
    {
        logger.trace( " Getting the total number of records" );
        long count = service.getCountOfEntities();
        logger.info( " The total number of records are : [" + count + "]" );
        return count;
    }

    //Deleting the user by Id
    @DeleteMapping("/{id}")
    public HttpStatus deleteUserById(@PathVariable("id") Long id) throws RecordNotFoundException
    {
        logger.trace( " The id of the user data to be deleted is :[" + id +"]"  );
        logger.info( " Deleting the user data with Id : [" + id + "]" );
        service.deleteUserById(id);

        return HttpStatus.OK;
    }


    // Insert user record
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user)
    {
        logger.info(" The new user details are as follows ..." );
        logger.info( " First Name : [" + user.getFirstName() + "]" );
        logger.info( " Last Name : [" + user.getLastName() + "]" );
        logger.info( " Phone Number : [" + user.getPhone() + "]" );
        logger.info( " Email Id : [" + user.getEmail() + "]" );

        logger.info( "Adding New User...." );

       User newUser = service.addUser(user);
       if(newUser!= null)
       {
           service.addUser(user);
           return newUser;
       }
       else
           {
               logger.error( " New user not added!!!"  );
               return service.addUser( user );
           }

    }


    //Updating a user's details
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User user) {

        try
        {
            //Displaying old User Data
            logger.info( " The old user details are : " );
            User oldEntity = service.getUserById(user.getId());
            logger.info( " First Name : [" + oldEntity.getFirstName() + "]" );
            logger.info( " Last Name : [" + oldEntity.getLastName() + "]" );
            logger.info( " Phone Number : [" + oldEntity.getPhone() + "]" );
            logger.info( " Email Id : [" + oldEntity.getEmail() + "]" );

            //Displaying new user data
            logger.info(" The new user details are as follows ..." );
            logger.info( " First Name : [" + user.getFirstName() + "]" );
            logger.info( " Last Name : [" + user.getLastName() + "]" );
            logger.info( " Phone Number : [" + user.getPhone() + "]" );
            logger.info( " Email Id : [" + user.getEmail() + "]" );

            logger.info( "Updating the User Details...." );

            service.updateUser(user);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        catch(NoSuchElementException ex)
        {
            // log the error message
            logger.error(ex.getMessage());
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }
    }




