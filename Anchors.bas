Type=Activity
Version=4.3
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
	Dim SQLExec As SQL

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private btnExit As Button
	
	Private spnAnchor1 As Spinner
	Private spnAnchor2 As Spinner
	Private spnAnchor3 As Spinner
	Private spnAnchor4 As Spinner
	
	Private lblAnch1x As Label
	Private lblAnch1y As Label
	Private lblAnch1z As Label
	Private lblAnch2x As Label
	Private lblAnch2y As Label
	Private lblAnch2z As Label
	Private lblAnch3x As Label
	Private lblAnch3y As Label
	Private lblAnch3z As Label
	Private lblAnch4x As Label
	Private lblAnch4y As Label
	Private lblAnch4z As Label
	
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("Anchors")

	' Open connection to Database
	SQLExec.Initialize(File.DirRootExternal, "EliteTrade.db", True)
	
	InitSpinners
	
	Functions.SetColours(Activity)
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub btnExit_Click
	Activity.Finish
	
End Sub

Sub InitSpinners
	Dim i As Int, j As Int
	Dim Query As String
	Dim CursAnchors As Cursor, CursSystems As Cursor
	
	Query = "SELECT * FROM Systems ORDER BY SystemName ASC"
	CursSystems = SQLExec.ExecQuery(Query)
	If CursSystems.RowCount > 0 Then
		spnAnchor1.Clear
		spnAnchor2.Clear
		spnAnchor3.Clear
		spnAnchor4.Clear
		spnAnchor1.Add("Not Set")
		spnAnchor2.Add("Not Set")
		spnAnchor3.Add("Not Set")
		spnAnchor4.Add("Not Set")
		For i = 0 To CursSystems.RowCount - 1
			CursSystems.Position = i
			spnAnchor1.Add(CursSystems.GetString("SystemName"))
			spnAnchor2.Add(CursSystems.GetString("SystemName"))
			spnAnchor3.Add(CursSystems.GetString("SystemName"))
			spnAnchor4.Add(CursSystems.GetString("SystemName"))
		Next
	End If
	spnAnchor1.SelectedIndex = 0
	spnAnchor2.SelectedIndex = 0
	spnAnchor3.SelectedIndex = 0
	spnAnchor4.SelectedIndex = 0
		
	Query = "SELECT A.AnchorID, A.SystemID, S.SystemName FROM Anchors A INNER JOIN Systems S ON A.SystemID = S.SystemID ORDER BY A.AnchorID"
	CursAnchors = SQLExec.ExecQuery(Query)
	
	If CursAnchors.RowCount > 0 Then
		For i = 0 To CursAnchors.RowCount - 1
			CursAnchors.Position = i
			If CursAnchors.GetInt("AnchorID") = 1 Then
				spnAnchor1.SelectedIndex = spnAnchor1.IndexOf(CursAnchors.GetString("SystemName"))
				For j = 0 To CursSystems.RowCount - 1
					CursSystems.Position = j
					If CursAnchors.GetString("SystemName") = CursSystems.GetString("SystemName") Then
						lblAnch1x.Text = NumberFormat2(CursSystems.GetDouble("SpaceX"),1,5,5,False)
						lblAnch1y.Text = NumberFormat2(CursSystems.GetDouble("SpaceY"),1,5,5,False)
						lblAnch1z.Text = NumberFormat2(CursSystems.GetDouble("SpaceZ"),1,5,5,False)
						Exit
					End If
				Next
			End If
			
			If CursAnchors.GetInt("AnchorID") = 2 Then
				spnAnchor2.SelectedIndex = spnAnchor2.IndexOf(CursAnchors.GetString("SystemName"))
				For j = 0 To CursSystems.RowCount - 1
					CursSystems.Position = j
					If CursAnchors.GetString("SystemName") = CursSystems.GetString("SystemName") Then
						lblAnch2x.Text = NumberFormat2(CursSystems.GetDouble("SpaceX"),1,5,5,False)
						lblAnch2y.Text = NumberFormat2(CursSystems.GetDouble("SpaceY"),1,5,5,False)
						lblAnch2z.Text = NumberFormat2(CursSystems.GetDouble("SpaceZ"),1,5,5,False)
						Exit
					End If
				Next
			End If
			
			If CursAnchors.GetInt("AnchorID") = 3 Then
				spnAnchor3.SelectedIndex = spnAnchor3.IndexOf(CursAnchors.GetString("SystemName"))
				For j = 0 To CursSystems.RowCount - 1
					CursSystems.Position = j
					If CursAnchors.GetString("SystemName") = CursSystems.GetString("SystemName") Then
						lblAnch3x.Text = NumberFormat2(CursSystems.GetDouble("SpaceX"),1,5,5,False)
						lblAnch3y.Text = NumberFormat2(CursSystems.GetDouble("SpaceY"),1,5,5,False)
						lblAnch3z.Text = NumberFormat2(CursSystems.GetDouble("SpaceZ"),1,5,5,False)
						Exit
					End If
				Next
			End If
			
			If CursAnchors.GetInt("AnchorID") = 4 Then
				spnAnchor4.SelectedIndex = spnAnchor4.IndexOf(CursAnchors.GetString("SystemName"))
				For j = 0 To CursSystems.RowCount - 1
					CursSystems.Position = j
					If CursAnchors.GetString("SystemName") = CursSystems.GetString("SystemName") Then
						lblAnch4x.Text = NumberFormat2(CursSystems.GetDouble("SpaceX"),1,5,5,False)
						lblAnch4y.Text = NumberFormat2(CursSystems.GetDouble("SpaceY"),1,5,5,False)
						lblAnch4z.Text = NumberFormat2(CursSystems.GetDouble("SpaceZ"),1,5,5,False)
						Exit
					End If
				Next
			End If
		Next
	End If
	CursSystems.Close
	CursAnchors.Close

End Sub

Sub spnAnchor1_ItemClick (Position As Int, Value As Object)
	Dim Query As String
	Dim CursSystems As Cursor
	Dim SystemIDNumber As Int
	Dim mp As Map
	
	mp.Initialize
	mp.Put("AnchorID", 1)
	Query = "SELECT * FROM Systems WHERE SystemName = '" & Value & "'"
	CursSystems = SQLExec.ExecQuery(Query)
	If CursSystems.RowCount = 1 Then
		CursSystems.Position = 0 'Sets the row pointer to the first record
		SystemIDNumber = CursSystems.GetInt("SystemID")
		lblAnch1x.Text = NumberFormat2(CursSystems.GetDouble("SpaceX"),1,5,5,False)
		lblAnch1y.Text = NumberFormat2(CursSystems.GetDouble("SpaceY"),1,5,5,False)
		lblAnch1z.Text = NumberFormat2(CursSystems.GetDouble("SpaceZ"),1,5,5,False)
		
		DBUtils.UpdateRecord(SQLExec, "Anchors", "SystemID", SystemIDNumber, mp)
	Else
		DBUtils.UpdateRecord(SQLExec, "Anchors", "SystemID", 0, mp)
	End If
	CursSystems.Close
End Sub

Sub spnAnchor2_ItemClick (Position As Int, Value As Object)
	Dim Query As String
	Dim CursSystems As Cursor
	Dim SystemIDNumber As Int
	Dim mp As Map
	
	mp.Initialize
	mp.Put("AnchorID", 2)
	Query = "SELECT * FROM Systems WHERE SystemName = '" & Value & "'"
	CursSystems = SQLExec.ExecQuery(Query)
	If CursSystems.RowCount = 1 Then
		CursSystems.Position = 0 'Sets the row pointer to the first record
		SystemIDNumber = CursSystems.GetInt("SystemID")
		lblAnch2x.Text = NumberFormat2(CursSystems.GetDouble("SpaceX"),1,5,5,False)
		lblAnch2y.Text = NumberFormat2(CursSystems.GetDouble("SpaceY"),1,5,5,False)
		lblAnch2z.Text = NumberFormat2(CursSystems.GetDouble("SpaceZ"),1,5,5,False)
		
		DBUtils.UpdateRecord(SQLExec, "Anchors", "SystemID", SystemIDNumber, mp)
	Else
		DBUtils.UpdateRecord(SQLExec, "Anchors", "SystemID", 0, mp)
	End If
	CursSystems.Close
End Sub

Sub spnAnchor3_ItemClick (Position As Int, Value As Object)
	Dim Query As String
	Dim CursSystems As Cursor
	Dim SystemIDNumber As Int
	Dim mp As Map
	
	mp.Initialize
	mp.Put("AnchorID", 3)
	Query = "SELECT * FROM Systems WHERE SystemName = '" & Value & "'"
	CursSystems = SQLExec.ExecQuery(Query)
	If CursSystems.RowCount = 1 Then
		CursSystems.Position = 0 'Sets the row pointer to the first record
		SystemIDNumber = CursSystems.GetInt("SystemID")
		lblAnch3x.Text = NumberFormat2(CursSystems.GetDouble("SpaceX"),1,5,5,False)
		lblAnch3y.Text = NumberFormat2(CursSystems.GetDouble("SpaceY"),1,5,5,False)
		lblAnch3z.Text = NumberFormat2(CursSystems.GetDouble("SpaceZ"),1,5,5,False)
		
		DBUtils.UpdateRecord(SQLExec, "Anchors", "SystemID", SystemIDNumber, mp)
	Else
		DBUtils.UpdateRecord(SQLExec, "Anchors", "SystemID", 0, mp)
	End If
	CursSystems.Close
End Sub

Sub spnAnchor4_ItemClick (Position As Int, Value As Object)
	Dim Query As String
	Dim CursSystems As Cursor
	Dim SystemIDNumber As Int
	Dim mp As Map
	
	mp.Initialize
	mp.Put("AnchorID", 4)
	Query = "SELECT * FROM Systems WHERE SystemName = '" & Value & "'"
	CursSystems = SQLExec.ExecQuery(Query)
	If CursSystems.RowCount = 1 Then
		CursSystems.Position = 0 'Sets the row pointer to the first record
		SystemIDNumber = CursSystems.GetInt("SystemID")
		lblAnch4x.Text = NumberFormat2(CursSystems.GetDouble("SpaceX"),1,5,5,False)
		lblAnch4y.Text = NumberFormat2(CursSystems.GetDouble("SpaceY"),1,5,5,False)
		lblAnch4z.Text = NumberFormat2(CursSystems.GetDouble("SpaceZ"),1,5,5,False)
		
		DBUtils.UpdateRecord(SQLExec, "Anchors", "SystemID", SystemIDNumber, mp)
	Else
		DBUtils.UpdateRecord(SQLExec, "Anchors", "SystemID", 0, mp)
	End If
	CursSystems.Close
End Sub
