#Backlog
A list of features that the application should have
##As an unauthorised user:
- I can see the log in page
- If I don't have an account I can create one by clicking on the registration button, from which I will be redirected to a for which needs to be filled with correct details (name, email, password)
- If I provide incorrect details (password is too small, name is already taken, email is already used) I should be alerted and prompted to input correct details
- If I do have an account I can provide my email/username and password, then by pressing the log in button I will be able to access my account
##As an authorised user (basic user - student):
Menu:
- I can press on a hamburger menu on the top left corner which will open and display the pages that I have access to
- The available pages should be: homepage, reserve a room, restaurants, friends, supplies

Homepage:
- I can see my reservations on the homepage on a calendar like structure that displays 30 days from the current day, the first row represents the current week
- I can manually add other events to the calendar (this way I know if my schedule overlaps with room reservations)
- I can filter my calendar: room reservations, manually added activities, friends invitations
- I can click on a certain reservation to see details (date, location, time) about it and invite friends to join

Reserve a room:
- I can filter the rooms depending on the facilities I need (capacity, restaurants –checkbox, other items available in the room)
- I can see the buildings after filtering, then when I press on one it should open a drop down menu where I can see all available rooms
- I can press a button to reserve a room and I am prompted with a calendar from where I can select a date and time
- When I click on a room, a pop-up with a detailed description of the room's features will appear
- I can only reserve a single room from a certain building each week, but I can reserve it for as long as I want on that day
- I can only reserve one room at a time in the same time interval
- I can reserve a room a week in advance

Restaurants:
- If I’m checked-in in a room, the building with the restaurants where that room is located will be showed first
- I can see all the buildings and their restaurants even though I am not currently in a room
- I can click on a restaurant and see the menu. If I am checked-in in that room I can also add items in the basket and then order (delivery or pick-up) *delivery time* - optional

Friends: 
- I can see a list of my friends
- I can delete a friend by pressing the delete button next to their name in the list
- I can add a friend by id or by name by pressing the add friend button on the top of the page

Supplies:
- I can see a list of available materials shown by building
- I can get the materials without checking-in in a room

##As an employee
– I can do everything a student does, but I can book the rooms one month in advance instead of a week

##As and admin
– I can do everything and employee does, but I can also have an admin panel where I can manage buildings, rooms and restaurants (basic admin functions)

##Specified features
- I have a hamburger menu to navigate through the application