# Chronometer-App

## Problems Identified:
1. Retrieving a string containing the time from the database and converting it to a Java time variable for comparison with the device's current time.
2. The status variable in the database is a boolean, not a string.
3. Time conversion issue with AM and PM; all values in the table are stored as strings.
4. Activating all alarms simultaneously may pose challenges.
5. Starting a service on its own is complex, and completion within the project timeline seems unlikely.
6. Lack of consideration for user interaction (editing or deleting alarms).
7. Services calling the database every time they start to determine active alarms.
8. Needing to start a service every time a new alarm is created.
9. Potential for multiple threaded services, requiring significant programming effort.
10. Services can only be destroyed from within the service itself, making managing the deactivate button challenging.
11. Issues with notifications, permissions, and handling sound functionality.
12. Stopping notifications or sound effectively involves starting another app within a service.

## Proposed Solution:
Considering the complexities and challenges identified, it might be more feasible to pivot the app's functionality to a simpler and user-friendly chronometer application. we can work on this as an indepandent project in the futer 

## Chronometer App Features:
- Set a specific time and start the countdown.
- The app continues counting down even when minimized or closed.
- Users can return to the app to find it still functioning.
- Option to reset the countdown time for editing.

This direction simplifies the functionality, making it easier to implement and ensuring a smoother user experience. Feel free to discuss and provide feedback on this proposed change.


