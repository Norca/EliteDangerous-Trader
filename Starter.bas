Type=Service
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
	#ExcludeFromLibrary: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim SQLExec As SQL
	Dim AnchorsDefined As Boolean
	Dim AnchorsNumber As Int
	Dim SystemMoves As Int

	Dim CurrLocation As String
	Dim MaxLYDist As Int
	Dim CurrStation As String

	'Colour information variables
	Dim BorderColour As Int
	Dim TextColour As Int
	Dim BackColour1 As Int
	Dim BackColour2 As Int
	Dim AlphaColour1 As Int
	Dim AlphaColour2 As Int
	Dim HighlightColour As Int
	

End Sub

Sub Service_Create
	'This is the program entry point.
	'This is a good place to load resources that are not specific to a single activity.
	AnchorsDefined = False	' Anchor Systems are not defined until service process has calculated them
	AnchorsNumber = 0
	SystemMoves = 0
	SQLExec.Initialize(File.DirRootExternal, "EliteTrade.db", True)	' Open connection to Database

	CurrLocation = " "
	CurrStation = "Not Set"
	MaxLYDist = 0

	BorderColour = Colors.RGB(0,100,0) 'Dark Green
	TextColour = Colors.RGB(255,255,255) 'White
	BackColour1 = Colors.RGB(0,100,0) 'Dark Green
	BackColour2 = Colors.RGB(46,139,87) 'Sea Green
	AlphaColour1 = Colors.ARGB(64,0,100,0) 'Transparent Dark Green
	AlphaColour2 = Colors.ARGB(64,46,139,87) 'Transparent Sea Green
	HighlightColour = Colors.RGB(124,252,0) 'Lawn Green

End Sub

Sub Service_Start (StartingIntent As Intent)
	
	If File.Exists(File.DirRootExternal, "EliteTrade.db") = False Then
		SQLExec.Initialize(File.DirRootExternal, "EliteTrade.db", True)
		EDTables.DatabaseSetup
	End If
	
	Dim Q As String

	Dim LocName As String
	Dim StatName As String
	Dim MaxDist As Int
	
	' Set Current System from Database
	Q = "SELECT SystemName FROM Location WHERE LocationID = 1"
	LocName = SQLExec.ExecQuerySingleResult(Q)
	CurrLocation = LocName
	'Set Current Station from Database
	Q = "SELECT StationName FROM Location WHERE LocationID = 1"
	StatName = SQLExec.ExecQuerySingleResult(Q)
	CurrStation = StatName
	' Set Current Max LY Trade Distance from Database
	Q = "SELECT TradeDistance FROM MaxLYTradeDistance WHERE MaxLYID = 1"
	MaxDist = SQLExec.ExecQuerySingleResult(Q)
	MaxLYDist = MaxDist

	' Show Main screen
	StartActivity(Main)
	
End Sub

'Return true to allow the OS default exceptions handler to handle the uncaught exception.
Sub Application_Error (Error As Exception, StackTrace As String) As Boolean
	Return True
End Sub

Sub Service_Destroy

End Sub
