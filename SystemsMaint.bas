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
	Dim alphalistSystems As List

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Private btnExit As Button
	Private btnAdd As Button
	Private btnCancel As Button
	Private btnDelete As Button
	Private btnPointInSpace As Button
	Private btnSave As Button

	Private edtSystemName As EditText
	
	Private lblX As Label
	Private lblY As Label
	Private lblZ As Label
	
	Private spnAllegiance As Spinner
	Private spnEconomy As Spinner
	Private spnGovernment As Spinner
	
	Private wbvSystemList As WebView
	
	Dim AddOREdit As Boolean 'True equals Add, False equals Edit
	Dim result As Boolean
	
	Dim PointExact As Int
	
	Dim oldGovernmentDesc As String
	Dim oldAllegDesc As String
	
	Dim Q As String

End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("SystemsMaint")
	
	InitSpinners

	Functions.SetColours(Activity)

	FillSystemsList

	PointExact = 0

	edtSystemName.Text = ""
	lblX.Text = ""
	lblY.Text = ""
	lblZ.Text = ""
	
	edtSystemName.Enabled = False
	spnGovernment.Enabled = False
	spnEconomy.Enabled = False
	spnAllegiance.Enabled = False
	btnPointInSpace.Enabled = False
	
	btnCancel.Visible = False
	btnDelete.Visible = False
	btnSave.Visible = False
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

#Region Setup

Sub InitSpinners
	Dim i As Int
	Dim CursGovernments As Cursor, CursEconomies As Cursor, CursAllegiances As Cursor
	
	'Government spinner
	Q = "SELECT * FROM Governments ORDER BY NumOfEntries DESC, GovDesc ASC"
	CursGovernments = Starter.SQLExec.ExecQuery(Q)
	If CursGovernments.RowCount > 0 Then
		spnGovernment.Clear
		spnGovernment.Add("Not Set")
		For i = 0 To CursGovernments.RowCount - 1
			CursGovernments.Position = i
			spnGovernment.Add(CursGovernments.GetString("GovDesc"))
		Next
	Else
		spnGovernment.Clear
		spnGovernment.Add("Empty")
	End If
	spnGovernment.SelectedIndex = 0
	CursGovernments.Close

	'Economy spinner
	Q = "SELECT * FROM Economies WHERE Surface = 0 ORDER BY NumOfEntries DESC, EconomyDesc ASC"
	CursEconomies = Starter.SQLExec.ExecQuery(Q)
	If CursEconomies.RowCount > 0 Then
		spnEconomy.Clear
		spnEconomy.Add("Not Set")
		For i = 0 To CursEconomies.RowCount - 1
			CursEconomies.Position = i
			spnEconomy.Add(CursEconomies.GetString("EconomyDesc"))
		Next
	Else
		spnEconomy.Clear
		spnEconomy.Add("Empty")
	End If
	spnEconomy.SelectedIndex = 0
	CursEconomies.Close
	
	'Allegiance spinner
	Q = "SELECT * FROM Allegiance ORDER BY NumOfEntries DESC, AllegDesc ASC"
	CursAllegiances = Starter.SQLExec.ExecQuery(Q)
	If CursAllegiances.RowCount > 0 Then
		spnAllegiance.Clear
		spnAllegiance.Add("Not Set")
		For i = 0 To CursAllegiances.RowCount - 1
			CursAllegiances.Position = i
			spnAllegiance.Add(CursAllegiances.GetString("AllegDesc"))
		Next
	Else
		spnAllegiance.Clear
		spnAllegiance.Add("Empty")
	End If
	spnAllegiance.SelectedIndex = 0
	CursAllegiances.Close
	
End Sub

Sub SystemsListAlpha
	Q = "SELECT * FROM Systems ORDER BY LYfromCurrent ASC, SystemName ASC"
	
	alphalistSystems = DBUtils.ExecuteMemoryTable(Starter.SQLExec, Q, Null, 0)
	
End Sub

Sub FillSystemsList
	Q = "SELECT S.SystemName AS [Star System], E.EconomyDesc AS [Economy], S.AllegDesc AS [Allegiance], S.GovDesc AS [Government], S.LYfromCurrent As [LY] FROM Systems S LEFT JOIN Economies E ON S.EconomyID = E.EconomyID ORDER BY S.LYfromCurrent ASC, S.SystemName ASC"
	wbvSystemList.LoadHtml(DBUtils.ExecuteHtml(Starter.SQLExec,Q,Null,0,True))
	
	SystemsListAlpha 'Sets the ID lookup list of the systems
	
End Sub

#End Region

#Region Buttons

Sub btnPointInSpace_Click

	Dim systemlocation As Coordinate
	systemlocation = Null

	btnPointInSpace.RequestFocus

	systemlocation = Elite.PlotNewSystem
	
	If systemlocation = Null Then
		lblX.Text = ""
		lblY.Text = ""
		lblZ.Text = ""
	Else
		lblX.Text = Round2(systemlocation.X,5)
		lblY.Text = Round2(systemlocation.Y,5)
		lblZ.Text = Round2(systemlocation.Z,5)
		PointExact = 1
	End If

End Sub

Sub btnAdd_Click
	AddOREdit = True 'Add new record
	
	btnAdd.Visible = False
	btnCancel.Visible = True
	btnSave.Visible = True
	edtSystemName.Enabled = True
	spnGovernment.Enabled = True
	spnEconomy.Enabled = True
	spnAllegiance.Enabled = True
	btnPointInSpace.Enabled = Starter.AnchorsDefined
	lblX.Text = ""
	lblY.Text = ""
	lblZ.Text = ""
	PointExact = 0
	
End Sub

Sub btnDelete_Click
	Dim Answ As Int
	
	Answ = Msgbox2("Do you really want to delete this System and associated stations & market info ?", "DELETE record", "Yes", "", "No", Null)
	
	If Answ = DialogResponse.POSITIVE Then
		
		Q = "DELETE FROM StockMarket WHERE SystemName = ?"
		Starter.SQLExec.ExecNonQuery2(Q, Array As String(edtSystemName.Text))
		Q = "DELETE FROM Stations WHERE SystemName = ?"
		Starter.SQLExec.ExecNonQuery2(Q, Array As String(edtSystemName.Text))

		Dim counter As Int
		Dim whereclause As Map

		' Reduce the old Station Type counter
		If oldGovernmentDesc <> "Not Set" Then
			Q = "SELECT NumOfEntries FROM Governments WHERE GovDesc = ?"
			counter = Starter.SQLExec.ExecQuerySingleResult2(Q, Array As String(oldGovernmentDesc))
			counter = counter - 1
			If counter < 0 Then
				counter = 0
			End If
			whereclause.Initialize
			whereclause.Put("GovDesc", oldGovernmentDesc)
			DBUtils.UpdateRecord(Starter.SQLExec,"Governments","NumOfEntries",counter,whereclause)
		End If

		' Reduce the old Station Type counter
		If oldAllegDesc <> "Not Set" Then
			Q = "SELECT NumOfEntries FROM Allegiance WHERE AllegDesc = ?"
			counter = Starter.SQLExec.ExecQuerySingleResult2(Q, Array As String(oldAllegDesc))
			counter = counter - 1
			If counter < 0 Then
				counter = 0
			End If
			whereclause.Initialize
			whereclause.Put("AllegDesc", oldAllegDesc)
			DBUtils.UpdateRecord(Starter.SQLExec,"Allegiance","NumOfEntries",counter,whereclause)
		End If
		
		Dim mp As Map
		mp.Initialize
		mp.Put("SystemName", edtSystemName.Text)
		DBUtils.DeleteRecord(Starter.SQLExec, "Systems", mp)

		FillSystemsList
	End If

	btnDelete.Visible = False
	btnCancel.Visible = False
	btnSave.Visible = False
	btnAdd.Visible = True
	edtSystemName.Text = ""
	spnGovernment.SelectedIndex = 0
	spnEconomy.SelectedIndex = 0
	spnAllegiance.SelectedIndex = 0
	edtSystemName.Enabled = False
	spnGovernment.Enabled = False
	spnEconomy.Enabled = False
	spnAllegiance.Enabled = False
	btnPointInSpace.Enabled = False
	lblX.Text = ""
	lblY.Text = ""
	lblZ.Text = ""

End Sub

Sub btnCancel_Click
	btnCancel.Visible = False
	btnDelete.Visible = False
	btnSave.Visible = False
	btnAdd.Visible = True
	edtSystemName.Text = ""
	spnGovernment.SelectedIndex = 0
	spnEconomy.SelectedIndex = 0
	spnAllegiance.SelectedIndex = 0
	edtSystemName.Enabled = False
	spnGovernment.Enabled = False
	spnEconomy.Enabled = False
	spnAllegiance.Enabled = False
	btnPointInSpace.Enabled = False
	lblX.Text = ""
	lblY.Text = ""
	lblZ.Text = ""
	PointExact = 0
	
End Sub

Sub btnSave_Click
	Dim counter As Int
	If AddOREdit = True Then
		'Insert new record
		If edtSystemName.Text.Length > 0 Then
			' edtSystemName.Text = edtSystemName.Text.Replace("'","")
			If Elite.SystemExists(edtSystemName.Text) = False Then
				Dim record As Map, whereclause As Map
	
				record.Initialize
				record.Put("SystemName", edtSystemName.Text)
				record.Put("GovDesc", spnGovernment.GetItem(spnGovernment.SelectedIndex))
				record.Put("EconomyID", Elite.FindEconomyNum(spnEconomy.GetItem(spnEconomy.SelectedIndex)))
				record.Put("AllegDesc", spnAllegiance.GetItem(spnAllegiance.SelectedIndex))
				record.Put("SpaceX", lblX.Text)
				record.Put("SpaceY", lblY.Text)
				record.Put("SpaceZ", lblZ.Text)
				record.Put("LYfromCurrent", 0)
				record.Put("ExactLocation", PointExact)
				SQLUtils.Table_InsertMap(Starter.SQLExec,"Systems",record)
				
				' Get current counter value of selected government type
				If spnGovernment.SelectedItem <> "Not Set" Then
					Q = "SELECT NumOfEntries FROM Governments WHERE GovDesc = ?"
					counter = Starter.SQLExec.ExecQuerySingleResult2(Q, Array As String(spnGovernment.SelectedItem))
					' Add one to the counter value
					counter = counter + 1
					' Set counter value of selected government type
					whereclause.Initialize
					whereclause.Put("GovDesc", spnGovernment.SelectedItem)
					DBUtils.UpdateRecord(Starter.SQLExec,"Governments","NumOfEntries",counter,whereclause)
				End If
				
				If spnAllegiance.SelectedItem <> "Not Set" Then
					' Get current counter value of selected allegiance type
					Q = "SELECT NumOfEntries FROM Allegiance WHERE AllegDesc = ?"
					counter = Starter.SQLExec.ExecQuerySingleResult2(Q, Array As String(spnAllegiance.SelectedItem))
					' Add one to the counter value
					counter = counter + 1
					' Set counter value of selected government type
					whereclause.Initialize
					whereclause.Put("AllegDesc", spnAllegiance.SelectedItem)
					DBUtils.UpdateRecord(Starter.SQLExec,"Allegiance","NumOfEntries",counter,whereclause)
				End If

			End If
			result = True
		Else
			Msgbox("Must enter a name for the Star System before save is possible", "A T T E N T I O N")
			result = False
		End If
	Else
		'Update existing record
		If edtSystemName.Text.Length > 0 Then
			If Elite.SystemExists(edtSystemName.Text) = True Then
				Dim record As Map, whereclause As Map

				If oldGovernmentDesc <> spnGovernment.SelectedItem And oldGovernmentDesc <> "Not Set" Then
					' Reduce the old Station Type counter
					Q = "SELECT NumOfEntries FROM Governments WHERE GovDesc = ?"
					counter = Starter.SQLExec.ExecQuerySingleResult2(Q, Array As String(oldGovernmentDesc))
					counter = counter - 1
					whereclause.Initialize
					whereclause.Put("GovDesc", oldGovernmentDesc)
					DBUtils.UpdateRecord(Starter.SQLExec,"Governments","NumOfEntries",counter,whereclause)
					' Increase the new Station Type counter
					Q = "SELECT NumOfEntries FROM Governments WHERE GovDesc = ?"
					counter = Starter.SQLExec.ExecQuerySingleResult2(Q, Array As String(spnGovernment.SelectedItem))
					counter = counter + 1
					whereclause.Initialize
					whereclause.Put("GovDesc", spnGovernment.SelectedItem)
					DBUtils.UpdateRecord(Starter.SQLExec,"Governments","NumOfEntries",counter,whereclause)
				End If

				If oldAllegDesc <> spnAllegiance.SelectedItem And oldAllegDesc <> "Not Set" Then
					' Reduce the old Station Type counter
					Q = "SELECT NumOfEntries FROM Allegiance WHERE AllegDesc = ?"
					counter = Starter.SQLExec.ExecQuerySingleResult2(Q, Array As String(oldAllegDesc))
					counter = counter - 1
					whereclause.Initialize
					whereclause.Put("AllegDesc", oldAllegDesc)
					DBUtils.UpdateRecord(Starter.SQLExec,"Allegiance","NumOfEntries",counter,whereclause)
					' Increase the new Station Type counter
					Q = "SELECT NumOfEntries FROM Allegiance WHERE AllegDesc = ?"
					counter = Starter.SQLExec.ExecQuerySingleResult2(Q, Array As String(spnAllegiance.SelectedItem))
					counter = counter + 1
					whereclause.Initialize
					whereclause.Put("AllegDesc", spnAllegiance.SelectedItem)
					DBUtils.UpdateRecord(Starter.SQLExec,"Allegiance","NumOfEntries",counter,whereclause)
				End If
				
				whereclause.Initialize
				whereclause.Put("SystemName", edtSystemName.Text)
				record.Initialize
				record.Put("GovDesc", spnGovernment.GetItem(spnGovernment.SelectedIndex))
				record.Put("EconomyID", Elite.FindEconomyNum(spnEconomy.GetItem(spnEconomy.SelectedIndex)))
				record.Put("AllegDesc", spnAllegiance.GetItem(spnAllegiance.SelectedIndex))
				record.Put("SpaceX", lblX.Text)
				record.Put("SpaceY", lblY.Text)
				record.Put("SpaceZ", lblZ.Text)
				record.Put("ExactLocation", PointExact)
				DBUtils.UpdateRecord2(Starter.SQLExec,"Systems",record,whereclause)
			Else
				Msgbox("Star System name can not be changed", "A T T E N T I O N")
				result = False
			End If
			result = True
		Else
			Msgbox("Star System name can not be blank", "A T T E N T I O N")
			result = False
		End If
	End If
	
	If result = True Then
		'Reset after save complete
		btnSave.Visible = False
		btnDelete.Visible = False
		btnCancel.Visible = False
		btnAdd.Visible = True
		edtSystemName.Text = ""
		spnGovernment.SelectedIndex = 0
		spnEconomy.SelectedIndex = 0
		spnAllegiance.SelectedIndex = 0
		edtSystemName.Enabled = False
		spnGovernment.Enabled = False
		spnEconomy.Enabled = False
		spnAllegiance.Enabled = False
		btnPointInSpace.Enabled = False
		lblX.Text = ""
		lblY.Text = ""
		lblZ.Text = ""
		
		FillSystemsList
	End If
	
End Sub

Sub btnExit_Click
	Activity.Finish

End Sub

#End Region

Sub wbvSystemList_OverrideUrl (Url As String) As Boolean
	'Prase the row and column numbers from the URL
	Dim Values() As String
	Dim Row As Int
	
	Values = Regex.Split("[.]", Url.SubString(7))
	Row = Values(1)
	
	Dim Val(8) As String
	
	Val = alphalistSystems.Get(Row)
	
	edtSystemName.Text = Val(0)
	spnGovernment.SelectedIndex = spnGovernment.IndexOf(Val(1)) 'Val(1) equals the Government Name
	spnAllegiance.SelectedIndex = spnAllegiance.IndexOf(Val(2)) 'Val(2) equals the Allegiance Name
	spnEconomy.SelectedIndex = spnEconomy.IndexOf(Elite.FindEconomy(Val(3))) 'Val(3) equals the Economy Number and needs to be converted to Economy Name

	'Lookup and create coordinate record for selected system
	Dim Curs As Cursor
	Dim SystemPoint As Coordinate
	Q = "SELECT * FROM Systems WHERE SystemName = ?"
	Curs = Starter.SQLExec.ExecQuery2(Q,Array As String(Val(0)))
	If Curs.RowCount > 0 Then
		Curs.Position = 0
		SystemPoint.Initialize(Curs.GetDouble("SpaceX"), Curs.GetDouble("SpaceY"), Curs.GetDouble("SpaceZ"))
		lblX.Text = SystemPoint.X
		lblY.Text = SystemPoint.Y
		lblZ.Text = SystemPoint.Z
	Else
		lblX.Text = Val(4)
		lblY.Text = Val(5)
		lblZ.Text = Val(6)
	End If
	
	If Val(1).Length < 1 Then
		oldGovernmentDesc = "Not Set"
	Else
		oldGovernmentDesc = Val(1)
	End If
	If Val(2).Length < 1 Then
		oldAllegDesc = "Not Set"
	Else	
		oldAllegDesc = Val(2)
	End If
	
	AddOREdit = False 'Edit existing record
	
	btnAdd.Visible = False
	btnDelete.Visible = True
	btnCancel.Visible = True
	btnSave.Visible = True
	edtSystemName.Enabled = True
	spnGovernment.Enabled = True
	spnEconomy.Enabled = True
	spnAllegiance.Enabled = True
	
	PointExact = Val(8)
	If PointExact = 0 Then
		btnPointInSpace.Enabled = Starter.AnchorsDefined
	Else
		btnPointInSpace.Enabled = False
	End If

	Return True 'Don't try to navigate to this URL

End Sub

Sub edtSystemName_EnterPressed
	edtSystemName.Text = edtSystemName.Text
	If Elite.SystemExists(edtSystemName.Text) = True Then
		edtSystemName.Text = ""
		edtSystemName.RequestFocus
	End If

End Sub