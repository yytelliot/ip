# Pookie Task Manager

> Your adorable task management companion! :3

Pookie is a friendly task management application with both CLI and GUI interfaces. Never forget a deadline or miss an event again with Pookie by your side! ^w^

![Pookie Interface](docs/Ui.png)

## Quick Start

1. Ensure you have Java 17 or above installed on your system
2. Download the latest `pookie.jar` from the [releases page](../../releases)
3. Run the application:
   ```bash
   java -jar pookie.jar
   ```
4. The GUI will appear. Type commands in the text field and press Enter or click Send
5. Type `help` to see all available commands

## Features

> **Notes about the command format:**
> * Words in `<angle brackets>` are parameters to be supplied by you.
>   * e.g., in `todo <description>`, `<description>` is a parameter: `todo read book`
> * Parameters must be in the specified order
> * Dates must be in `yyyy-MM-dd` format (e.g., `2026-02-15`)

### Viewing help: `help`

Shows a comprehensive list of all available commands with examples.

**Format:** `help`

**Example:**
```
help
```

---

### Adding a todo task: `todo`

Adds a simple task without any date/time constraints.

**Format:** `todo <description>`

**Examples:**
* `todo read book`
* `todo buy groceries`
* `todo call dentist`

**Expected output:**
```
Noted! I added a new task :3
  [T][ ] read book
You now have 1 tasks in your list! uwu
```

---

### Adding a deadline task: `deadline`

Adds a task that must be completed by a specific date.

**Format:** `deadline <description> /by <date>`

* Date must be in `yyyy-MM-dd` format

**Examples:**
* `deadline submit report /by 2026-02-20`
* `deadline complete assignment /by 2026-03-15`

**Expected output:**
```
Noted! I added a new task :3
  [D][ ] submit report (by: Feb 20 2026)
You now have 2 tasks in your list! uwu
```

---

### Adding an event task: `event`

Adds a task that occurs during a specific time period.

**Format:** `event <description> /from <start date> /to <end date>`

* Both dates must be in `yyyy-MM-dd` format

**Examples:**
* `event team meeting /from 2026-02-18 /to 2026-02-18`
* `event conference /from 2026-03-01 /to 2026-03-03`

**Expected output:**
```
Noted! I added a new task :3
  [E][ ] team meeting (from: Feb 18 2026 to: Feb 18 2026)
You now have 3 tasks in your list! uwu
```

---

### Listing all tasks: `list`

Displays all tasks in your task list with their completion status.

**Format:** `list`

**Example:**
```
list
```

**Expected output:**
```
Here's what you have in your list! *^w^*
1. [T][ ] read book
2. [D][ ] submit report (by: Feb 20 2026)
3. [E][ ] team meeting (from: Feb 18 2026 to: Feb 18 2026)
```

---

### Marking tasks as done: `mark`

Marks one or more tasks as completed. Supports single indices, multiple indices, and ranges.

**Format:** `mark <index> [<index> ...] [<start>-<end> ...]`

* The index refers to the task number shown in the `list` command
* Index must be a positive integer (1, 2, 3, ...)
* Can specify multiple indices separated by spaces
* Can use ranges with `-` (e.g., `2-4` marks tasks 2, 3, and 4)
* Can mix individual indices and ranges

**Examples:**
* `mark 1` - marks task 1 as done
* `mark 1 3 5` - marks tasks 1, 3, and 5 as done
* `mark 2-4` - marks tasks 2, 3, and 4 as done
* `mark 1 3-5 7` - marks tasks 1, 3, 4, 5, and 7 as done

**Expected output:**
```
Nice! Pookie will mark this task as done x3
1. [T][X] read book
```

---

### Unmarking tasks: `unmark`

Marks one or more tasks as not done. Supports the same syntax as `mark`.

**Format:** `unmark <index> [<index> ...] [<start>-<end> ...]`

**Examples:**
* `unmark 1` - marks task 1 as not done
* `unmark 2-4` - marks tasks 2, 3, and 4 as not done
* `unmark 1 3 5` - marks tasks 1, 3, and 5 as not done

**Expected output:**
```
Okay! Pookie will unmark this task as not done. x3
1. [T][ ] read book
```

---

### Deleting tasks: `delete`

Permanently removes one or more tasks from your list. Supports the same syntax as `mark`.

**Format:** `delete <index> [<index> ...] [<start>-<end> ...]`

**Examples:**
* `delete 2` - deletes task 2
* `delete 1 3` - deletes tasks 1 and 3
* `delete 2-4` - deletes tasks 2, 3, and 4

**Expected output:**
```
I've deleted this task! >:3
2. [D][ ] submit report (by: Feb 20 2026)
Now you have 2 tasks in your list! uwu
```

---

### Finding tasks: `find`

Searches for tasks that contain a specific keyword in their description.

**Format:** `find <keyword>`

* The search is case-insensitive
* Only the task description is searched

**Examples:**
* `find book` - finds all tasks containing "book"
* `find meeting` - finds all tasks containing "meeting"

**Expected output:**
```
Pookie found some tasks! :3
1. [T][ ] read book
2. [T][ ] return book
```

---

### Exiting the program: `bye`

Exits the application after a 5-second delay.

**Format:** `bye` or `kthxbye`

**Example:**
```
bye
```

**Expected output:**
```
Bye bye! TwT
```

The application will close automatically after 5 seconds.

---

## Data Storage

* Tasks are automatically saved to `pookie_data.txt` in the application directory
* Data is loaded automatically when you start Pookie
* No manual saving is required - everything is automatic! ^w^

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| **help** | `help` | `help` |
| **todo** | `todo <description>` | `todo read book` |
| **deadline** | `deadline <description> /by <date>` | `deadline submit report /by 2026-02-20` |
| **event** | `event <description> /from <date> /to <date>` | `event meeting /from 2026-02-18 /to 2026-02-18` |
| **list** | `list` | `list` |
| **mark** | `mark <index/range> ...` | `mark 1`, `mark 1 3-5` |
| **unmark** | `unmark <index/range> ...` | `unmark 2`, `unmark 1-3` |
| **delete** | `delete <index/range> ...` | `delete 2`, `delete 1 3 5` |
| **find** | `find <keyword>` | `find book` |
| **exit** | `bye` or `kthxbye` | `bye` |

---

## Setting up for Development

**Prerequisites:** JDK 17 or above

1. Fork this repository and clone it to your local machine
2. Open the project in IntelliJ IDEA:
   * Click `File` > `Open`
   * Select the project directory
   * Click `OK`
3. Configure JDK 17:
   * Go to `File` > `Project Structure` > `Project`
   * Set SDK to JDK 17
   * Set language level to `SDK default`
4. Build the project:
   * Run `./gradlew build` (Unix/Mac) or `gradlew.bat build` (Windows)
5. Run the application:
   * Locate `src/main/java/pookie/ui/Launcher.java`
   * Right-click and select `Run 'Launcher.main()'`

## Building the JAR

```bash
./gradlew shadowJar
```

The JAR file will be created in `build/libs/pookie.jar`

---

Made with ♥ and lots of uwu energy
