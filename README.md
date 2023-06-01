# LingoFriends

Descriptions:

This is a project for people who want to learn foreign languages on their own and share interesting information.

## Database

Application use PostgreSQL database. For start the application you need Postgres server (jdbc:postgresql://localhost:5432/freelance_db) with created database 'freelance_db'. Database contains five tables.

* Table _users_table_ - contains information about application users;
* Table _course_table_ - contains information about application courses;
* Table _lesson_table_ - contains information about application lessons;
* Table _subscription_table_ - contains information about expiry date (for the future application scale);
* Table _l_users_course_table_ - link table between users and course (user can add any course to their favourite).

## Available endpoints for users

* http://localhost:8080/user/res/all - GET method, show all available users for users
* http://localhost:8080/user/res/{id} - GET method, show information about one user for users
* http://localhost:8080/user/res/name/{name} - GET method, show information about one user by username for users
* http://localhost:8080/user/res/ln/{languageName} - GET method, show information about one user by language name for users
* http://localhost:8080/user - POST method, create user
* http://localhost:8080/user - PUT method, update user
* http://localhost:8080/user/{id} - DELETE method, delete current user's account
* http://localhost:8080/user/addcourse - POST method, add service to user's favourite
* http://localhost:8080/user/getCourse/{userId} - GET method, show information about favorite courses for user
* http://localhost:8080/user/changePassword - PUT method, change user's password
 
* http://localhost:8080/course/res/all - GET method, show all available courses for users
* http://localhost:8080/course/res/{id} - GET method, show information about one course for users
* http://localhost:8080/course/res/ln/{languageName} - GET method, show information about one course by language name for users
* http://localhost:8080/course/res/us/{userId} - GET method, show information about one course by username for users
* http://localhost:8080/course - POST method, create course
* http://localhost:8080/course - PUT method, update course
* http://localhost:8080/course/{id} - DELETE method, delete current course

* http://localhost:8080/lesson/res/all - GET method, show all available lessons for users
* http://localhost:8080/lesson/res/{id} - GET method, show information about one lesson for users
* http://localhost:8080/lesson/res/cr/{courseId} - GET method, show information about one course by course id for users
* http://localhost:8080/lesson/res/us/{userId} - GET method, show information about one lesson by username for users
* http://localhost:8080/lesson - POST method, create course
* http://localhost:8080/lesson - PUT method, update course
* http://localhost:8080/lesson/{id} - DELETE method, delete current course

* http://localhost:8080/subscription/res/{id} - GET method, show information about one subscription for user
* http://localhost:8080/subscription/us/{userId} - GET method, show information about one subscription by user id for users
* http://localhost:8080/subscription - POST method, create course
* http://localhost:8080/subscription - PUT method, update course
* http://localhost:8080/subscription/{id} - DELETE method, delete current course

## Available endpoints for admin

* http://localhost:8080/user/admin/all - GET method, show all available users for admin
* http://localhost:8080/user/res/all - GET method, show all available users for users
* http://localhost:8080/user/admin/{id} - GET method, show information about one user for admin
* http://localhost:8080/user/res/{id} - GET method, show information about one user for users
* http://localhost:8080/user/res/name/{name} - GET method, show information about one user by username for users
* http://localhost:8080/user/res/ln/{languageName} - GET method, show information about one user by language name for users
* http://localhost:8080/user/create - POST method, create user
* http://localhost:8080/user/update - PUT method, update user
* http://localhost:8080/user/{id} - DELETE method, delete current user's account
* http://localhost:8080/user/addcourse - POST method, add service to user's favourite
* http://localhost:8080/user/getCourse/{userId} - GET method, show information about favorite courses for user
* http://localhost:8080/user/changePassword - PUT method, change user's password

* http://localhost:8080/course/admin/all - GET method, show all available courses for admin
* http://localhost:8080/course/res/all - GET method, show all available courses for users
* http://localhost:8080/course/admin/{id} - GET method, show information about one course for admin
* http://localhost:8080/course/res/{id} - GET method, show information about one course for users
* http://localhost:8080/course/res/ln/{languageName} - GET method, show information about one course by language name for users
* http://localhost:8080/course/admin/us/{userId} - GET method, show information about one course by username for admin
* http://localhost:8080/course/res/us/{userId} - GET method, show information about one course by username for users
* http://localhost:8080/course/create - POST method, create course
* http://localhost:8080/course/update - PUT method, update course
* http://localhost:8080/course/{id} - DELETE method, delete current course

* http://localhost:8080/lesson/admin/all - GET method, show all available lesson for admin
* http://localhost:8080/lesson/res/all - GET method, show all available lessons for users
* http://localhost:8080/lesson/admin/{id} - GET method, show information about one lesson for admin
* http://localhost:8080/lesson/res/{id} - GET method, show information about one lesson for users
* http://localhost:8080/lesson/admin/cr/{courseId} - GET method, show information about one lesson by course id for admin
* http://localhost:8080/lesson/res/cr/{courseId} - GET method, show information about one course by course id for users
* http://localhost:8080/lesson/res/us/{userId} - GET method, show information about one lesson by username for users
* http://localhost:8080/lesson/create - POST method, create course
* http://localhost:8080/lesson/update - PUT method, update course
* http://localhost:8080/lesson/{id} - DELETE method, delete current course

* http://localhost:8080/subscription/admin/all - GET method, show all available subscription for admin
* http://localhost:8080/subscription/admin/{id} - GET method, show information about one subscription for admin
* http://localhost:8080/subscription/res/{id} - GET method, show information about one subscription for user
* http://localhost:8080/subscription/us/{userId} - GET method, show information about one subscription by user id for users
* http://localhost:8080/subscription - POST method, create course
* http://localhost:8080/subscription - PUT method, update course
* http://localhost:8080/subscription/{id} - DELETE method, delete current course

