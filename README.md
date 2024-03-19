# JournalApp

This android app is to keep track of what you are doing or even as a ToDo Application.
1. You can add, modify and delete entries
2. All these entries would persist in your phone reliably
3. You can also share your activities with your friends!

## Collaborators

Aryan Shelke (2020B4A71203G), Email: f20201203@goa.bits-pilani.ac.in

Pratham Bhatnagar (2020A7PS1222G), Email: f20201222@goa.bits-pilani.ac.in

## About the Assignment

### How the tasks were completed?

Actions in the Navgraph:

We primarily implemented these actions using onClick methods for buttons, invoking the Navigator to execute the desired action. Additionally, for menu items, we integrated Navigator actions into specific menu item listeners.

Room Database:

We organized the data into four columns: UUID, title, startTime, and endTime. The startTime and endTime values were stored as Date objects, allowing us to encapsulate the "date" within these time parameters.

Delete Button in Menu Bar:

The delete button triggers a Navgraph action, leading the user to an alert dialog to confirm item deletion. We designed this as an independent dialog fragment. If the user confirms deletion ("yes" on the dialog), they are redirected to the entry list screen, another action within the nav graph.

Share Button in Menu Bar:

The implementation of the share button followed a similar approach, with no alterations to the navgraph. All menu bar items were cleared and added in every fragment's "onCreateView" as the application utilizes a single action bar. The share button employs intents to send data in plain text to compatible applications.

Info Button in Menu Bar:

The implementation of the Info button mirrored that of the Delete button.

Accessibility:

Talkback:

Talkback integration progressed smoothly, except for some challenges in the list view. Accidentally pressing the time button did not provide information about the corresponding entry, suggesting room for improvement.

Accessibility Scanner Issues:

Addressing contrast issues, we modified colors across the app to enhance accessibility.

Espresso Tests:

We created five espresso tests covering most application functionalities, excluding the share button. Pressing the share button caused the espresso test to pause, as it ventured beyond the test's scope when share options became available. Additionally, a test verifying the proper functioning of the info screen occasionally failed without a clear cause, despite flawless performance during regular app usage.

### Assignment difficulty

Assignment was decently difficult, and we faced a lot of small issues which hindered the progress a lot, we took around 12-15 hours to complete the assignment.

Assignment difficulty: 9/10, quite a big jump from assignment 3.