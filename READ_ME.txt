1. In order to run project, you should build and package the project and put 'webapp.war' to 'webapps' folder of tomcat and run tomcat.
    Web app homepage is available at 'http://localhost:8080/webapp/'
2. I used ElephantSql as remote database for project which is based on PostgreSQL, so obviously it's not necessary to create test data. It will be already created.
    Unfortunately free version allow us to work with only one connection. So, it is necessary to avoid double connection.
    (In case of problems with access to database, there is sql script, which creates local database for testing webapp: 'resources\sql\questionnaire.sql')
    (Please feel free to contact me if you need any further information)
3. Next step, you should specify folder for storage of responses file in 'web.xml'. This file contains users questionnaire responses.
    I decided to store it as text Json files, because PostgreSQL doesn't allow me to store data with changeable fields (column).
4. Next step, you should specify folder for logs storage. It can be done in log4j.properties resources folder.



SHORT DESCRIPTION:

Project has 3 models (1 enum), and 3 tables in database respectively.
There are 'dao' layer for operating with database and file storage, and service layer for interconnection with the 'controller' layer.

'Controller' layer has beans with main logic.
    -'UserManager' manage user session and other related information.
    (UserManager class are command containers. It implements Commands design pattern.)
    -'QuestionnaireManager' manage questionnaire information and other related information.
    (Unfortunately, I didn't have enough time to do refactoring for this class, so it is just God Object.
    But it also can be done using Command design pattern.)

'Beans' folder has dto for filling out form.
    There are bean for all input form (login, signUp, editProfile, changePassword) questionnaireForm inputted in ordinary HashMap.
    Also there is a bean for temporary storage of records in order to speed up the access to this data, because reading from file requires a lot of time.

'Utils' folder has 4 utils classes for different types of needs.
    -'Constants' has main constant.
    -'HibernateUtil' has methods for getting Session Factory.
    -'Validator' was designed for validate form and contains methods for validating login, registration, editProfile, changePassword and questionnaire form.
    -'Util' consists of methods for converting dto beans to models class.
    -'EmailNotificator' has methods for email notification.
    (Email notification was designed by using Gmail smtp)


Made corrections:

1) webapp name can be changed in pom.xml <build> -> <finalName>
or by renaming the war file.
2) questionnaire responses is stored in PostgreSQL database and columns of 'response' table are changeable, so they can be deleted, created and updated dynamically.

also I added confirmation on deleting field and made some refactoring of source code.