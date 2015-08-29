# goto
Welcome to goto. This is a command line program that enables a user to browse the web from the command line.

## General information

Enter a command in the format `goto <site name/URL>` or `goto <flag> <...extra arguments...>`.
Entering a command in the format `goto <site name/URL>` will open a new tab in your default browser,
and you will be automatically navigated the to the given site.

You do not need to type in a fully qualified URL for the site name. You can simply type
in the site name, and a default prefix (`http://www.`) and suffix (`.com`) will automatically be added.
Any different prefixes or suffixes that you add yourself will override the default
prefixes and suffixes.

For example, entering `goto somesite`, `goto somesite.com`, or `goto www.somesite.com`, etc. will
open up your default browser and navigate to `http://www.somesite.com`.

Similarly, entering `goto somesite.net` will navigate your default browser to
`http://www.somesite.net`, and entering `goto https://www.somesite.net` will navigate
your default browser to `https://www.somesite.net`.

This program also has a bookmarking system, wherein you can register aliases to use in place of a
site name or URL. For example, if you have registered `ggl` as an alias for the
URL `http://www.google.com`, you can simply type in `goto ggl` and the browser will navigate to
`http://www.google.com`. Here, alias-URL pairs are referred to as bookmarks.

The saved bookmarks are stored and recorded in a file in your home directory.

You are able to create new bookmarks, and view or delete existing ones
through the program flags. These flags are listed below, along with their
descriptions and how to use them.

### NOTE:
If any of your arguments contain a special character (such as an ampersand), you must surround
the special character (or the whole argument) in single quotes so that the program is able to process
the character as intended. For example, if your original entry is `goto google.com/exa&mple`, you
must instead input `goto 'google.com/exa&mple'`. This is unavoidable due to the nature of the
command line, and must be done for the program to work as intended.

## Usage

### No flags:
##### Usage: `goto <site name/URL>`
**Description:** Opens a new tab in your default browser at the given site. You do not need to fully
qualify the URL, as the program will format your entry for you and add a default prefix (`http://www.`)
and suffix (`.com`) to the site name if you have not provided your own. For example, `goto google`
becomes `goto http://www.google.com`.

### Bookmark flag: --bookmark
##### Usage: `goto --bookmark <alias> <URL>`
**Description:** Saves an alias-URL pair to a bookmark file in your home directory. If the alias given
already exists, the old URL is overwritten with the new one given. Program flags cannot be registered
as aliases.

### Delete bookmark flag: --delete-bookmark
##### Usage: `goto --delete-bookmark <alias>`
**Description:** Deletes an existing, currently saved alias. If the alias given does not exist,
a message will be output saying that the alias does not exist.

### View bookmarks flag: --view-bookmarks
##### Usage: `goto --view-bookmarks`
**Description:** Display all currently saved bookmarks.

### Help flag: --help
##### Usage: `goto --help`
**Description:** Get help with goto (see this information again).
