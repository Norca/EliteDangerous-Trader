Type=Activity
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: True
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

	Dim CommodLst As List
End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private btnExit As Button
	Private wbvCommodList As WebView
	
	Dim Q As String
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("CommodUpdate")
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub wbvCommodList_OverrideUrl (Url As String) As Boolean
	'Prase the row and column numbers from the URL
	Dim Values() As String
	Dim Row As Int
	
	Values = Regex.Split("[.]", Url.SubString(7))
	Row = Values(1)
	
	Dim Val(3) As String
	
	Val = CommodLst.Get(Row)
	


	Return True 'Don't try to navigate to this URL
	
End Sub

Sub btnExit_Click
	Activity.Finish
End Sub

Sub FillCommodList
	Q = "SELECT ComGrpDesc, CommodDesc, Trading FROM Commodities ORDER BY ComGrpDesc ASC, CommodDesc ASC"
	wbvCommodList.LoadHtml(DBUtils.ExecuteHtml(Starter.SQLExec,Q,Null,0,True))
	
	CommodList 'Sets the ID lookup list of the commodities
End Sub

Sub CommodList
	Q = "SELECT * FROM Commodities ORDER BY ComGrpDesc ASC, CommodDesc ASC"
	
	CommodLst = DBUtils.ExecuteMemoryTable(Starter.SQLExec, Q, Null, 0)
End Sub