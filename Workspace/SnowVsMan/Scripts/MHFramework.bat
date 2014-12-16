rem WHY NOT ROBOCOPY?
rem Because robocopy doesn't exist on Windows XP.  :(

rem ===============================
rem Grab all the framework stuff.
rem ===============================

cd ..
md bin

md bin\mhframework
md bin\mhframework\ai
md bin\mhframework\event
md bin\mhframework\gui
md bin\mhframework\images
md bin\mhframework\images\fonts
md bin\mhframework\images\fonts\AndroidNation
md bin\mhframework\images\fonts\NES1
md bin\mhframework\images\fonts\OCRGreen
md bin\mhframework\images\fonts\TahomaBlue
md bin\mhframework\io
md bin\mhframework\io\net
md bin\mhframework\io\net\client
md bin\mhframework\io\net\event
md bin\mhframework\io\net\server
md bin\mhframework\media
md bin\mhframework\tilemap

copy ..\mhframework\bin\mhframework\*.class                    .\bin\mhframework
copy ..\mhframework\bin\mhframework\ai\*.class                 .\bin\mhframework\ai
copy ..\mhframework\bin\mhframework\event\*.class              .\bin\mhframework\event
copy ..\mhframework\bin\mhframework\gui\*.class                .\bin\mhframework\gui
copy ..\mhframework\bin\mhframework\images\*.*                 .\bin\mhframework\images
copy ..\mhframework\bin\mhframework\images\fonts               .\bin\mhframework\images\fonts
copy ..\mhframework\bin\mhframework\images\fonts\AndroidNation .\bin\mhframework\images\fonts\AndroidNation
copy ..\mhframework\bin\mhframework\images\fonts\NES1          .\bin\mhframework\images\fonts\NES1
copy ..\mhframework\bin\mhframework\images\fonts\OCRGreen      .\bin\mhframework\images\fonts\OCRGreen
copy ..\mhframework\bin\mhframework\images\fonts\TahomaBlue    .\bin\mhframework\images\fonts\TahomaBlue
copy ..\mhframework\bin\mhframework\io\*.class                 .\bin\mhframework\io
copy ..\mhframework\bin\mhframework\io\net\*.class                .\bin\mhframework\net
copy ..\mhframework\bin\mhframework\io\net\client\*.class         .\bin\mhframework\net\client
copy ..\mhframework\bin\mhframework\io\net\event\*.class         .\bin\mhframework\net\common
copy ..\mhframework\bin\mhframework\io\net\server\*.class         .\bin\mhframework\net\server
copy ..\mhframework\bin\mhframework\media\*.class              .\bin\mhframework\media
copy ..\mhframework\bin\mhframework\tilemap\*.class            .\bin\mhframework\tilemap

rem C:\"Program Files (x86)"\Java\jdk1.6.0_22\bin\jar cvf mhframework.jar mhframework
rem pause

