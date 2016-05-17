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

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private btnExit As Button
	Private btnNext As Button
	Private btnPrev As Button
	Private btnFirst As Button
	Private btnPrevSect As Button
	Private btnNextSect As Button
	Private btnLast As Button
	
	Private lblCommod As Label
	Private lblCommodGroup As Label
	Private lblComGrp As Label
	Private lblDemand As Label
	Private lblSupply As Label
	
	Private spnStation As Spinner
	
	Private tbnNA As ToggleButton
	Private tbnDemHigh As ToggleButton
	Private tbnDemMed As ToggleButton
	Private tbnDemLow As ToggleButton
	Private tbnSupHigh As ToggleButton
	Private tbnSupMed As ToggleButton
	Private tbnSupLow As ToggleButton
	
	Private wbvStockList As WebView
	
	Private acetSystemName As AutoCompleteEditText
	
	Dim SystemName As String
	Dim StationName As String
	Dim SMLMax As Int
	Dim SMLPosition As Int
	Dim SMItem As Int
	
	Dim LoadedType As String
	Dim LoadedLvl As Int
	Dim ItemChanged As Boolean
	
	Dim SystemLocation As List
	Dim StationMarketList As List
	
	Dim pnlDemand As Panel
	Dim pnlSupply As Panel

	Dim Q As String

End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("StockMarket")
		
	LoadSystemLocation
	InitSystemLocation
	SMLPosition = 0
	lblCommodGroup.Text = ""
	lblComGrp.Text = ""
	
	Functions.SetColours(Activity)
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

#Region Setup

Sub LoadSystemLocation
	acetSystemName.Text = Starter.CurrLocation
	acetSystemName_EnterPressed
	
End Sub

Sub InitSystemLocation
	Dim i As Int
	Dim CursSystems As Cursor
	
	SystemLocation.Initialize
	' Systems list
	Q = "SELECT SystemName FROM Systems WHERE LYfromCurrent < " & Starter.MaxLYDist & " ORDER BY SystemName ASC"
	CursSystems = Starter.SQLExec.ExecQuery(Q)
	If CursSystems.RowCount > 0 Then
		SystemLocation.Add("Not Set")
		For i = 0 To CursSystems.RowCount - 1
			CursSystems.Position = i
			SystemLocation.Add(CursSystems.GetString("SystemName"))
		Next
	End If
	SystemLocation.Set(0,"")
	CursSystems.Close
	
End Sub

Sub FillMarketList
	EditMarketList ' Populates the list of Commodities that can be edited
	Q = "SELECT SM.CommodDesc AS [Commodity], SM.StockTypeDesc AS [Demand/Supply], SL.StockLevelDesc AS [Stock Level] FROM StockMarket SM LEFT JOIN StockLevels SL ON SM.StockLevelRank = SL.StockLevelRank WHERE SM.StationName = ? AND SM.ComGrpDesc = ? AND SM.StockTypeDesc != 'Not Tradeable' ORDER BY SM.ComGrpDesc, SM.CommodDesc ASC"
	wbvStockList.LoadHtml(DBUtils.ExecuteHtml(Starter.SQLExec, Q, Array As String(StationName,lblCommodGroup.Text), 0, False))

End Sub

Sub EditMarketList
	Q = "SELECT SM.RecordID, SM.StockTypeDesc, SM.StockLevelRank, SM.ComGrpDesc, SM.CommodDesc FROM StockMarket SM WHERE SM.StationName = ? ORDER BY SM.ComGrpDesc, SM.CommodDesc ASC"
	StationMarketList = DBUtils.ExecuteMemoryTable(Starter.SQLExec, Q, Array As String(StationName), 0)
	SMLMax = StationMarketList.Size - 1
	If SMLMax <= 0 Then
		EditVisible(False)	
	Else
		EditVisible(True)
		DisplayEditItem(SMLPosition)
	End If
	
End Sub

#End Region

#Region Buttons

Sub btnPrev_Click
	If SMLPosition > 0 Then
		SMLPosition = SMLPosition - 1
		FillMarketList
	End If
End Sub

Sub btnNext_Click
	If SMLPosition < SMLMax Then
		SMLPosition = SMLPosition + 1
		FillMarketList
	End If
End Sub

Sub btnFirst_Click
	SMLPosition = 0
	FillMarketList
End Sub

Sub btnLast_Click
	SMLPosition = SMLMax
	FillMarketList
End Sub

Sub btnPrevSect_Click
	Dim val(5) As String
	Dim currCommodGroup As String
	Dim newCommodGroup As String
	Dim changed = False As Boolean
	If SMLPosition > 0 Then
		Do While changed = False
			val = StationMarketList.Get(SMLPosition)
			currCommodGroup = val(3).ToUpperCase
			SMLPosition = SMLPosition - 1
			If SMLPosition = 0 Then
				changed = True
			End If
			val = StationMarketList.Get(SMLPosition)
			newCommodGroup = val(3).ToUpperCase
			If newCommodGroup <> currCommodGroup Then
				changed = True
			End If
		Loop
	End If
	FillMarketList
End Sub

Sub btnNextSect_Click
	Dim val(5) As String
	Dim currCommodGroup As String
	Dim newCommodGroup As String
	Dim changed = False As Boolean
	If SMLPosition < SMLMax Then
		Do While changed = False
			val = StationMarketList.Get(SMLPosition)
			currCommodGroup = val(3).ToUpperCase
			SMLPosition = SMLPosition + 1
			If SMLPosition = SMLMax Then
				changed = True
			End If
			val = StationMarketList.Get(SMLPosition)
			newCommodGroup = val(3).ToUpperCase
			If newCommodGroup <> currCommodGroup Then
				changed = True
			End If
		Loop
	End If
	FillMarketList
End Sub

Sub btnExit_Click
	Activity.Finish
	
End Sub

#End Region

#Region ToggleButtons

Sub tbnNA_CheckedChange(Checked As Boolean)
	If Checked = True Then
		tbnDemHigh.Checked = False
		tbnDemMed.Checked = False
		tbnDemLow.Checked = False
		tbnSupHigh.Checked = False
		tbnSupMed.Checked = False
		tbnSupLow.Checked = False
		SetToggleButtonColour
	End If
	If Checked = False Then
		SetToggleButtonColour
	End If
End Sub

Sub tbnDemHigh_CheckedChange(Checked As Boolean)
	If Checked = True Then
		tbnNA.Checked = False
		tbnDemMed.Checked = False
		tbnDemLow.Checked = False
		tbnSupHigh.Checked = False
		tbnSupMed.Checked = False
		tbnSupLow.Checked = False
		SetToggleButtonColour
	End If
End Sub

Sub tbnDemMed_CheckedChange(Checked As Boolean)
	If Checked = True Then
		tbnNA.Checked = False
		tbnDemHigh.Checked = False
		tbnDemLow.Checked = False
		tbnSupHigh.Checked = False
		tbnSupMed.Checked = False
		tbnSupLow.Checked = False
		SetToggleButtonColour
	End If
End Sub

Sub tbnDemLow_CheckedChange(Checked As Boolean)
	If Checked = True Then
		tbnNA.Checked = False
		tbnDemHigh.Checked = False
		tbnDemMed.Checked = False
		tbnSupHigh.Checked = False
		tbnSupMed.Checked = False
		tbnSupLow.Checked = False
		SetToggleButtonColour
	End If
End Sub

Sub tbnSupHigh_CheckedChange(Checked As Boolean)
	If Checked = True Then
		tbnNA.Checked = False
		tbnDemHigh.Checked = False
		tbnDemMed.Checked = False
		tbnDemLow.Checked = False
		tbnSupMed.Checked = False
		tbnSupLow.Checked = False
		SetToggleButtonColour
	End If
End Sub

Sub tbnSupMed_CheckedChange(Checked As Boolean)
	If Checked = True Then
		tbnNA.Checked = False
		tbnDemHigh.Checked = False
		tbnDemMed.Checked = False
		tbnDemLow.Checked = False
		tbnSupHigh.Checked = False
		tbnSupLow.Checked = False
		SetToggleButtonColour
	End If
End Sub

Sub tbnSupLow_CheckedChange(Checked As Boolean)
	If Checked = True Then
		tbnNA.Checked = False
		tbnDemHigh.Checked = False
		tbnDemMed.Checked = False
		tbnDemLow.Checked = False
		tbnSupHigh.Checked = False
		tbnSupMed.Checked = False
		SetToggleButtonColour
	End If
End Sub

#End Region

Sub SetToggleButtonColour
	SaveItem

	Dim ButtonSelected As Boolean
	
	ButtonSelected = False
	
	Select True
		Case tbnNA.Checked = True
			ButtonSelected = True
		Case tbnDemHigh.Checked = True
			ButtonSelected = True
		Case tbnDemMed.Checked = True
			ButtonSelected = True
		Case tbnDemLow.Checked = True
			ButtonSelected = True
		Case tbnSupHigh.Checked = True
			ButtonSelected = True
		Case tbnSupMed.Checked = True
			ButtonSelected = True
		Case tbnSupLow.Checked = True
			ButtonSelected = True
	End Select
	
	If ButtonSelected = True Then
		tbnNA.Background = Functions.ToggleButGood
		tbnDemHigh.Background = Functions.ToggleButGood
		tbnDemMed.Background = Functions.ToggleButGood
		tbnDemLow.Background = Functions.ToggleButGood
		tbnSupHigh.Background = Functions.ToggleButGood
		tbnSupMed.Background = Functions.ToggleButGood
		tbnSupLow.Background = Functions.ToggleButGood
	Else
		tbnNA.Background = Functions.ToggleBut
		tbnDemHigh.Background = Functions.ToggleBut
		tbnDemMed.Background = Functions.ToggleBut
		tbnDemLow.Background = Functions.ToggleBut
		tbnSupHigh.Background = Functions.ToggleBut
		tbnSupMed.Background = Functions.ToggleBut
		tbnSupLow.Background = Functions.ToggleBut
	End If
End Sub

Sub DisplayEditItem(SMLNum As Int)
	Dim Val(5) As String
	
	Val = StationMarketList.Get(SMLNum)
	
	SMItem = Val(0)
	lblComGrp.Text = Val(3).ToUpperCase
	lblCommodGroup.Text = Val(3)
	lblCommod.Text = Val(4).ToUpperCase
	LoadedType = Val(1)
	LoadedLvl = Val(2)

	Select True
		Case Val(1) = "Not Tradeable"
			tbnNA.Checked = True
		Case Val(1) = "Demand"
			Select True
				Case Val(2) = 4
					tbnDemHigh.Checked = True
				Case Val(2) = 2
					tbnDemMed.Checked = True
				Case Val(2) = 1
					tbnDemLow.Checked = True
			End Select
		Case Val(1) = "Supply"
			Select True
				Case Val(2) = 4
					tbnSupHigh.Checked = True
				Case Val(2) = 2
					tbnSupMed.Checked = True
				Case Val(2) = 1
					tbnSupLow.Checked = True
			End Select
		Case Val(1) = ""
			tbnNA.Checked = False
			tbnDemHigh.Checked = False
			tbnDemMed.Checked = False
			tbnDemLow.Checked = False
			tbnSupHigh.Checked = False
			tbnSupMed.Checked = False
			tbnSupLow.Checked = False
	End Select
	
	' SetToggleButtonColour
	
	EditNav

End Sub

Sub EditVisible(Status As Boolean)
	If Status = True Then
		lblCommodGroup.Visible = True
		lblCommod.Visible = True
		tbnNA.Visible = True
		tbnDemHigh.Visible = True
		tbnDemMed.Visible = True
		tbnDemLow.Visible = True
		tbnSupHigh.Visible = True
		tbnSupMed.Visible = True
		tbnSupLow.Visible = True
		btnPrev.Visible = True
		btnNext.Visible = True
		btnFirst.Visible = True
		btnLast.Visible = True
		btnPrevSect.Visible = True
		btnNextSect.Visible = True
		pnlDemand.Visible = True
		pnlSupply.Visible = True
	Else
		lblCommodGroup.Visible = False
		lblCommod.Visible = False
		tbnNA.Visible = False
		tbnDemHigh.Visible = False
		tbnDemMed.Visible = False
		tbnDemLow.Visible = False
		tbnSupHigh.Visible = False
		tbnSupMed.Visible = False
		tbnSupLow.Visible = False
		btnPrev.Visible = False
		btnNext.Visible = False
		btnFirst.Visible = False
		btnLast.Visible = False
		btnPrevSect.Visible = False
		btnNextSect.Visible = False
		pnlDemand.Visible = False
		pnlSupply.Visible = False
	End If
	
End Sub

Sub EditNav

	If SMLPosition = 0 Then
		btnPrev.Enabled = False
		btnPrevSect.Enabled = False
		btnFirst.Enabled = False
	Else
		btnPrev.Enabled = True
		btnPrevSect.Enabled = True
		btnFirst.Enabled = True
	End If
	If SMLPosition = SMLMax Then
		btnNext.Enabled = False
		btnNextSect.Enabled = False
		btnLast.Enabled = False
	Else
		btnNext.Enabled = True
		btnNextSect.Enabled = True
		btnLast.Enabled = True
	End If	
	
End Sub

Sub SaveItem
		Dim StockTypeValue As String
		Dim StockLevelValue As Int
		Dim whereclause As Map

		whereclause.Initialize
		whereclause.Put("RecordID", SMItem)

		Select True
			Case tbnNA.Checked = True
				StockTypeValue = "Not Tradeable"
				StockLevelValue = 0
			Case tbnDemHigh.Checked = True
				StockTypeValue = "Demand"
				StockLevelValue = 4
			Case tbnDemMed.Checked = True
				StockTypeValue = "Demand"
				StockLevelValue = 2
			Case tbnDemLow.Checked = True
				StockTypeValue = "Demand"
				StockLevelValue = 1
			Case tbnSupHigh.Checked = True
				StockTypeValue = "Supply"
				StockLevelValue = 4
			Case tbnSupMed.Checked = True
				StockTypeValue = "Supply"
				StockLevelValue = 2
			Case tbnSupLow.Checked = True
				StockTypeValue = "Supply"
				StockLevelValue = 1
		End Select

		If StockTypeValue <> LoadedType Then
			ItemChanged = True
		End If
		If StockLevelValue <> LoadedLvl Then
			ItemChanged = True
		End If

	If ItemChanged = True Then
		DBUtils.UpdateRecord(Starter.SQLExec, "StockMarket", "StockTypeDesc", StockTypeValue, whereclause)
		DBUtils.UpdateRecord(Starter.SQLExec, "StockMarket", "StockLevelRank", StockLevelValue, whereclause)
		ItemChanged = False
		btnNext_Click
	End If
	
End Sub

Sub spnStation_ItemClick (Position As Int, Value As Object)
	Dim i As Int
	Dim CursStations As Cursor
	Dim CursStock As Cursor
	Dim CursCommod As Cursor
	Dim record As Map
	Dim econnum As Int
	
	Q = "SELECT * FROM Stations WHERE StationName = ?"
	CursStations = Starter.SQLExec.ExecQuery2(Q, Array As String(Value))
	If CursStations.RowCount > 0 Then
		CursStations.Position = 0 'Sets the row pointer to the first record
		StationName = CursStations.GetString("StationName")
		SystemName = CursStations.GetString("SystemName")
		econnum = CursStations.GetInt("EconomyNum")
	End If
	CursStations.Close
	Q = "SELECT * FROM StockMarket WHERE StationName = ?"
	CursStock = Starter.SQLExec.ExecQuery2(Q, Array As String(StationName))
	If econnum <> 0 Then
		If CursStock.RowCount = 0 Then
			Q = "SELECT C.CommodDesc, C.ComGrpDesc FROM Commodities C ORDER BY C.ComGrpDesc, C.CommodDesc ASC"
			CursCommod = Starter.SQLExec.ExecQuery(Q)
			If CursCommod.RowCount > 0 Then
				record.Initialize
				record.Put("RecordID", Null)
				record.Put("SystemName", SystemName)
				record.Put("StationName", StationName)
				For i = 0 To CursCommod.RowCount - 1
					CursCommod.Position = i
					record.Put("ComGrpDesc", CursCommod.GetString("ComGrpDesc"))
					record.Put("CommodDesc", CursCommod.GetString("CommodDesc"))
					record.Put("StockTypeDesc", "")
					record.Put("StockLevelRank", 0)
					SQLUtils.Table_InsertMap(Starter.SQLExec,"StockMarket",record)
	
				Next
			End If
			CursCommod.Close
		Else
			Dim R As String
			Dim totalcount As Int
			Q = "SELECT C.CommodDesc, C.ComGrpDesc FROM Commodities C ORDER BY C.ComGrpDesc, C.CommodDesc ASC"
			CursCommod = Starter.SQLExec.ExecQuery(Q)
			If CursCommod.RowCount > 0 Then
				record.Initialize
				For i = 0 To CursCommod.RowCount - 1
					CursCommod.Position = i
					R = "SELECT COUNT(*) FROM StockMarket WHERE StationName = ? AND CommodDesc = ?"
					totalcount = Starter.SQLExec.ExecQuerySingleResult2(R, Array As String(StationName,CursCommod.GetString("CommodDesc")))
					If totalcount = 0 Then
						record.Put("RecordID", Null)
						record.Put("SystemName", SystemName)
						record.Put("StationName", StationName)
						record.Put("ComGrpDesc", CursCommod.GetString("ComGrpDesc"))
						record.Put("CommodDesc", CursCommod.GetString("CommodDesc"))
						record.Put("StockTypeDesc", "")
						record.Put("StockLevelRank", 0)
						SQLUtils.Table_InsertMap(Starter.SQLExec,"StockMarket",record)
					End If
				Next
			End If
			CursCommod.Close
		End If
		CursStock.Close
	End If

	SMLPosition = 0

	FillMarketList
	
End Sub

Sub acetSystemName_ItemClick (Value As String)
	Dim i As Int
	Dim CursStation As Cursor
	
	' Station spinner
	Q = "SELECT * FROM Stations WHERE SystemName = ? AND EconomyNum > 0 ORDER BY StationName ASC"
	CursStation = Starter.SQLExec.ExecQuery2(Q, Array As String(Starter.CurrLocation))
	If CursStation.RowCount > 0 Then
		spnStation.Clear
		spnStation.Add("Not Set")
		For i = 0 To CursStation.RowCount - 1
			CursStation.Position = i
			spnStation.Add(CursStation.GetString("StationName"))
		Next
	End If
	spnStation.SelectedIndex = 0
	CursStation.Close

End Sub

Sub acetSystemName_EnterPressed
	Dim i As Int
	Dim CursStation As Cursor
	
	' Station spinner
	Q = "SELECT * FROM Stations WHERE SystemName = ? AND EconomyNum > 0 ORDER BY StationName ASC"
	CursStation = Starter.SQLExec.ExecQuery2(Q, Array As String(Starter.CurrLocation))
	If CursStation.RowCount > 0 Then
		spnStation.Clear
		spnStation.Add("Not Set")
		For i = 0 To CursStation.RowCount - 1
			CursStation.Position = i
			spnStation.Add(CursStation.GetString("StationName"))
		Next
	End If
	spnStation.SelectedIndex = 0
	CursStation.Close
	
End Sub
