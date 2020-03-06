##Overview
A list of features and requirements that the application need to fulfil. The application is a room reservation system for TU Delft. 

#Authentication feature
- An unauthenticated user should be able to create an account and then use it to access the features of the application
- An unauthenticated user will use the registration form which can be accessed by clicking on the registration button below the login form
- The user will be prompted to add the following details: username, type (student, employee, admin) email, password, date of birth. The details will be checked and if found valid they will be stored in the database (the password is going to be hashed)
- The validation can be done on multiple levels (client, server and database constraints)
---
- The user which already posses an account can log in via the login form. His details will be validated and if the user is found in the database then access will be granted form him to access his account
- The password should be hashed and then checked with the hashed password that exists in the database
- If the supplied information is not valid the user will be prompted with a message such as: "Invalid username or password"

**The following features are only available for the authenticated user**
#Menu
- Represents the main navigation system for the application
- A hamburger type menu, can be accessed by clicking on the classic hamburger menu icon on the top left part of the screen. After clicking on it, the menu will slide from the side or roll from above
- The menu will have the following tabs: 'Overview', 'Room reservation', 'Restaurants', 'Supplies' (more might be added in the future). The has one more tab: 'Admin panel'

#Room reservation feature
- Available in the 'Room reservation' tab
- GUI - The user will see a list of buildings; each building having its own list of rooms below it. On the left side of the screen there is a filtering menu where the user can refine the search
- Clicking on a building displays information about the room (capacity, facilities (e.g. computers, projector, whiteboard) and amount of each facility)
- The user has to select a date and a time in the filtering section and then he will only see the filtered list of rooms. After that he can press the reserve button next to the room that he wants to reserve. Finally the reservation will be sent to the server, validated and inserted in the database
- The filtering enables the user to select facilities that a room should have, capacity, the buildings in which the rooms should be, if the room needs to be in a building where restaurants exist
- Students can book a room one week in advance, employees and admins can reserve one month in advance
- The user cannot reserve two rooms at the same time
- The user should not be able to reserve rooms in the same building more than once a week

#Reserved rooms feature
- Available in the 'Overview' tab
- GUI - The user is presented with a calendar like structure where he can see the reservation he has made a month in advance: The first row represents the current week, with 3 more rows following. On the left side another filtering option is available
- GUI - each box in the calendar represents a day and events will be represented as boxes in each square. When the user presses on the event a pop-up appears that shows details about it such as date, time, room and building
- The user can filter the calendar by clicking on check-boxes depending on what he wants to see (events, reservations and invitations). When different boxes are going to be checked, the information will go to the server and a filtering operation will define the displayed information
- There are 3 types of events: room reservations, invitations, manually added events
- Invitations: a user can invite his friends to join his room reservation. This can be done by clicking on an event and clicking the edit invites button. The user can then add or remove friends from his room reservation
- Manually added events: A user can add his own events to the calendar (details to be supplied: name, starting time, end time). This can be done by pressing on the 'Add event' button in the top left corner of the screen. The events can overlap with other events, including room reservations
- When the users gets on this page all its data is going to be fetched from the database and displayed in the calendar

#Restaurants feature
- Available in the 'Restaurants' tab
- GUI - The user should be able to see a list of all the buildings that have a restaurant, and also a list of all the restaurants below each building. If the user is checked-in a room inside a building with a restaurant, that building with its restaurants is the first to be shown. The rest of the buildings will be listed after this building in a separate section which is separated by a message (e.g. ‘You cannot order from these restaurants because you are not checked in their adjacent buildings, but you can still look at the menu’) 
- The user can only order from restaurants that are in the same building as the room they are checked-in. If the user is not checked-in a room, he may still see the menu, but he cannot order
- When the user clicks on a restaurant he is redirected to the menu. 
- GUI - The restaurant page will have a list of all the available dishes. Each dish will have a name and a description. There is a button next to it or below it to add it to basket. On the right side of the screen, the basket enables the user to see what items he has added, the total price and the amount from each. There is a 'Order' button which submit the user's order
- The user can only order from a restaurant that is in the same building as the room he is checked-in into. He can still see the menus of all the restaurants but only the ones that he can order from will have the basket displayed
- The price in the basket will be updated in real time. When clicks the order button, order will be processed by the server and saved in the database and a confirmation message will appear

#Friendship feature
- Available in the 'Friends' tab
- GUI - A list of friends stacked one on top of the other (like a stack). A delete button next to each friends name. An add friend button on the top left corner of the screen from which a pop-up asking for another user's username appears.
- When adding a friend, if the supplied username does not exist, the user will be alerted that the username is invalid

#Item reservation feature
- Available in the 'Supplies' tab
- GUI - A list of buildings which in turn have a list of items (name, stock) stacked on top of each other (like a stack)
- The user can reserve an item even if he does not have a room reservation active at the moment in any building
- The user can reserve from any building 
- The user click on an item, choose the amount, date and time and then reserve the item, just like in Rooms reservation feature
- If the user selects an amount of a certain item higher than the available stock then he will be alerted
- The user sees the items that are currently reserved by him first, then the rest of the buildings and their available items

#Admin panel feature
- Available in the 'Admin panel' tab
- The admin panel enables the administrator of the application to add, update and delete buildings, rooms, supplies, equipment, users (not sure yet) and also retrieve information about them
