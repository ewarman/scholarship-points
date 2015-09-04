# scholarship-points
A program to track Kappa Phi Delta's scholarship points. Uses Selenium to count emails from sisters, attribute points, and calculate team scores.

My sorority has a contest every semester to encourage scholarship. The scholarship chair (me this semester) picks a location every week. If sisters take a photo of themselves studying in that place, they get a point. Winners get sorority swag.

Before I wrote this program, the scholarship chair had to manually tally the points. Sisters sent the points via email, text, even Snapchat. There was no way I was doing all that work every week so I wrote a program to do it for me! Not only does the program accurately count all of the points, but it allowed me to start more than one competion. I've added actives vs. pledges and in house vs. out of house competitions in addition to the random four teams we had last semester.

I was curious about Selenium so I tryed it out, but it may have been easier to add more functionality if I used the Gmail API and OAuth to scrape my inbox. UI scraping on Gmail is tough because of the dynamically created HTML tags, so some attributes of an email are hard to grab. For example, I couldn't grab email addresses, only the names that showed up in the inbox. Selenium works for this project because sisters can send me emails from multiple accounts as long as their email name is in the format "FirstName LastName".

Future items to improve/add:
1. Iterating through lists faster -- maybe change the data structure or at least have more probes in my search algorithms (recursion would be sweet).
2. Verifying each email has a .jpg or .png attachment and saving each photo to a file
3. Multiple attachments = multiple points: allowing 3 attached photos on one email to count as 3 points.

