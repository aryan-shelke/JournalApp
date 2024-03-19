# JournalApp

**JournalApp** is an Android application designed to help users keep track of their activities or use it as a ToDo application.

- Users can add, modify, and delete entries to manage their tasks effectively.
- All entries persist reliably on the user's phone, ensuring data integrity and accessibility.
- Additionally, users have the option to share their activities with friends, facilitating collaboration and communication.

---

Welcome to the README for the **Journal App**. This document provides an overview of the app's implementation details, including navigation, database structure, menu bar functionalities, accessibility features, and espresso tests.

## Actions in the Navgraph:

Actions within the Navgraph are primarily implemented using `onClick` methods for buttons, invoking the Navigator to execute the desired action. Additionally, for menu items, Navigator actions are integrated into specific menu item listeners.

## Room Database:

The data within the app is organized into four columns within a Room database: UUID, title, startTime, and endTime. The startTime and endTime values are stored as Date objects, allowing encapsulation of the "date" within these time parameters.

## Delete Button in Menu Bar:

The delete button in the menu bar triggers a Navgraph action, leading the user to an alert dialog to confirm item deletion. This is designed as an independent dialog fragment. If the user confirms deletion ("yes" on the dialog), they are redirected to the entry list screen, another action within the Navgraph.

## Share Button in Menu Bar:

The share button follows a similar implementation approach, with no alterations to the Navgraph. All menu bar items are cleared and added in every fragment's `onCreateView` as the application utilizes a single action bar. The share button employs intents to send data in plain text to compatible applications.

## Info Button in Menu Bar:

The implementation of the Info button mirrors that of the Delete button.

## Accessibility:

### Talkback:

Talkback integration progressed smoothly, except for some challenges in the list view. Accidentally pressing the time button did not provide information about the corresponding entry, suggesting room for improvement.

### Accessibility Scanner Issues:

To address contrast issues, colors across the app were modified to enhance accessibility.

## Espresso Tests:

Five espresso tests cover most application functionalities, excluding the share button. Pressing the share button caused the espresso test to pause, as it ventured beyond the test's scope when share options became available. Additionally, a test verifying the proper functioning of the info screen occasionally failed without a clear cause, despite flawless performance during regular app usage.
