package com.Impelsys.UserDemo.Service;

import com.Impelsys.UserDemo.Exception.RecordNotFoundException;
import com.Impelsys.UserDemo.Model.User;
import com.Impelsys.UserDemo.Repository.UserRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService
{
    public static final Logger logger = LogManager.getLogger( UserService.class );

    @Autowired
    UserRepository repository;

    //Method to add new user
    public User addUser(User user)
    {

        return repository.save( user );
    }

    //Method to get all users
    public List <User> getAllUsers(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase( Sort.Direction.ASC.name() ) ? Sort.by( sortBy ).ascending() :
                    Sort.by( sortBy ).descending();

        Pageable paging = PageRequest.of( pageNo, pageSize, sort );

        Page <User> pagedResult = repository.findAll( paging );

        //Returning the user data if present
        if (pagedResult.hasContent())
        {
            logger.info( "User data fetched successfully" );
            return pagedResult.getContent();
        }
        else
            //Returning an empty list, since no data available
            {
                logger.info( " No user data available" );
                return new ArrayList <User>();
            }
    }

    public List <User> listAll(Integer pageNo, Integer pageSize, String sortBy,
                               String sortDirection, String keyword)
    {
        Sort sort = sortDirection.equalsIgnoreCase( Sort.Direction.ASC.name() ) ?
                                         Sort.by( sortBy ).ascending() : Sort.by( sortBy ).descending();
        Pageable paging = PageRequest.of( pageNo, pageSize, sort );
        Page <User> pagedResult;

        //If keyword is specified, retrieve the user data containing the keyword
        if (keyword != null)
            {
                pagedResult = repository.search( keyword, paging );
            }
        else
            //if keyword is not specified , retrieve all the user data
            {
                logger.trace("Keyword not specified.Hence retrieving all the user data..");
                pagedResult = repository.findAll( paging );
            }

        if (pagedResult.hasContent())
        {
            //Returning the data that is retrieved
            logger.info( " Searching Complete!!!" );
            return pagedResult.getContent();
        }
        else
            {
                //No data found, Hence returning empty list
                logger.trace( "No user data available!!!" );
                return new ArrayList <User>();
            }

    }


    @Transactional(readOnly = true)
    public long getCountOfEntities()
    {
        long count = repository.count();
        return count;
    }

   //Method to get user using Id
    public User getUserById(Long id) throws RecordNotFoundException
    {
        //Finding user details of specified details
        Optional <User> employee = repository.findById( id );

        //return the user details if it is present
        if (employee.isPresent())
        {
            logger.info( "User Data Found!!! Returning the user details with Id : [" + id + "]" );
            return employee.get();
        }
        else
            //display message is user id is not found
            {
                throw new RecordNotFoundException( "No employee record exist for given id" );
            }
    }

    public void deleteUserById(Long id) throws RecordNotFoundException
    {
        //Finding user details of specified details
        Optional <User> user = repository.findById( id );

        //Delete the user details if the Id is found
        if (user.isPresent())
        {
            repository.deleteById( id );
            logger.info( "Successfully Deleted user with Id : [" + id + "] !!!" );
        }
        else
            //If Id is not found, display the message
            {
                throw new RecordNotFoundException( "No employee record exist for given id" );
            }
    }

    //Method to update the user details
    public User updateUser(User newUser)
    {
        long id = newUser.getId();

        logger.trace( " The id of the user whose data must be updated is : [" + id + "]" );

        //Finding data with the particular user id
        logger.info( "Finding user data with Id : [" + id + "]" );
        Optional <User> user = repository.findById( id );

        //Updating the user details,if present
        if (user.isPresent())
        {
            User newEntity = user.get();
            newEntity.setFirstName( newUser.getFirstName() );
            newEntity.setLastName( newUser.getLastName() );
            newEntity.setEmail( newUser.getEmail() );
            newEntity.setPhone( newUser.getPhone() );

            newEntity = repository.save( newEntity );
            logger.info( "User details updated successfully !!! " );

            return newEntity;

        }
        else
            //If user id is not found, display unsuccessful message.
            {
            logger.info( " The user data is not updated since the record for the given Id does not exist " );
            throw new RecordNotFoundException( " The record with Id [" + id + "] does not exist" );
            }

    }
}
