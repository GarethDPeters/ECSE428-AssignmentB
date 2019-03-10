Feature: Outlook sending of an email with a file attachment


  # Normal flow
  Scenario Outline: Composing and sending a new email with a file attachment from computer
    Given I am on the Outlook Inbox page
    When I press on the New Message button
    And I enter "<email>" as email recipient address
    And I enter a subject "test1"
    And I choose "<file>" from my computer using the attachment button
    When I press Send
    Then the recipient will receive an "<email>" with subject "test1" and the appropriate "<file>"

    Examples: 
      | email                        | file          |
      | garethdpeters@gmail.com      | testfile1.txt |
      | gareth.peters@hotmail.com    | testfile2.txt |
      | gareth.peters@mail.mcgill.ca | testfile3.txt |
      | ecse428dummy@gmail.com       | testfile4.txt |
      | ecse428dummy@hotmail.com     | testfile5.txt |

  # Alternate flow
  Scenario Outline: Composing and sending a new email with two or more file attachments
    Given I am on the Outlook Inbox page
    When I press on the New Message button
    And I enter "<email>" as email recipient address
    And I enter a subject "test2"
    And I choose "<file>" from my computer using the attachment button
    And I choose "<file2>" from my computer using the attachment button
    When I press Send
    Then the recipient will receive an "<email>" with subject "test2" and the appropriate "<file>" and "<file2>"

    Examples: 
      | email                        | file          | file2         |
      | garethdpeters@gmail.com      | testfile1.txt | testfile2.txt |
      | gareth.peters@hotmail.com    | testfile2.txt | testfile3.txt |
      | gareth.peters@mail.mcgill.ca | testfile3.txt | testfile4.txt |
      | ecse428dummy@gmail.com       | testfile4.txt | testfile5.txt |
      | ecse428dummy@hotmail.com     | testfile5.txt | testfile1.txt |

  # Error flow
  Scenario Outline: Sending an email with blank email textbox
    Given I am on the Outlook Inbox page
    When I press on the New Message button
    And I enter a subject "RaNdOm_SuBjEcT1238704"
    And I choose "<file>" from my computer using the attachment button
    When I press Send
    Then I will get an error message stating I have not entered any recipient
    And the email with subject "RaNdOm_SuBjEcT1238704" will not be sent

    Examples: 
      | file          |
      | testfile1.txt |
      | testfile2.txt |
      | testfile3.txt |
      | testfile4.txt |
      | testfile5.txt |
