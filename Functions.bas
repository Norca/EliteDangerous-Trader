Type=StaticCode
Version=5.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub


Sub StationExists(StatName As String) As Boolean
	Dim Query As String
	Dim CursTempStations As Cursor
	Dim result As Boolean
	Dim SQLExec As SQL
	' Open connection to Database
	SQLExec.Initialize(File.DirRootExternal, "EliteTrade.db", True)

	'Checks if the System Name already exists
	'Query = "SELECT StationName FROM TempStations WHERE StationName ='" & StatName & "'"
	'CursTempStations = SQLExec.ExecQuery(Query)
	Query = "SELECT StationName FROM TempStations WHERE StationName = ?"
	CursTempStations = Starter.SQLExec.ExecQuery2(Query, Array As String(StatName))
	If CursTempStations.RowCount > 0 Then
		Msgbox("This Station already exists in the Star System", "A T T E N T I O N")
		result = True
	Else
		result = False
	End If
	CursTempStations.Close
	Return result

End Sub


Sub FindCommodNum(CommodName As String) As Int
	Dim I As Int
	Dim Q As String
	Dim result As Int
	Dim CursCommod As Cursor
	Dim SQLExec As SQL
	' Open connection to Database
	SQLExec.Initialize(File.DirRootExternal, "EliteTrade.db", True)
	
	result = 0
	Q = "SELECT * FROM Commodities ORDER BY CommodDesc ASC"
	CursCommod = SQLExec.ExecQuery(Q)
	If CursCommod.RowCount > 0 Then
		For I = 0 To CursCommod.RowCount - 1
			CursCommod.Position = I
			If CommodName = CursCommod.GetString("CommodDesc") Then
				result = CursCommod.GetInt("CommodID")
				Exit
			End If
		Next
	End If
	CursCommod.Close
	Return result
	
End Sub


Sub IntToBool(Num As Int) As Boolean
	If Num = 1 Then
		Return True
	Else
		Return False
	End If
	' Num = Array As Boolean(False, True)
End Sub

Sub BoolToInt(Check As Boolean) As Int
    If (Check) Then
        Return 1
    Else
        Return 0
    End If
End Sub


Sub ToggleBut As StateListDrawable
	'Define Disabled state
	Dim StateDisabled As ColorDrawable
	StateDisabled.Initialize(Starter.AlphaColour2, 5)
	'Define Checked state
	Dim StateChecked As ColorDrawable
	StateChecked.Initialize2(Starter.BackColour2, 5, 3, Starter.HighlightColour)
	'Define Unchecked state
	Dim StateUnchecked As ColorDrawable
	StateUnchecked.Initialize(Starter.BackColour1, 5)

	Dim TogBut As StateListDrawable
	TogBut.Initialize
	TogBut.AddState(TogBut.State_Disabled, StateDisabled)
	TogBut.AddState(TogBut.State_Checked, StateChecked)
	TogBut.AddState(TogBut.State_Unchecked, StateUnchecked)
	Return TogBut
	
End Sub

Sub Spin As StateListDrawable
	'Define Disabled state
	Dim StateDisabled As ColorDrawable
	StateDisabled.Initialize(Starter.AlphaColour2, 3)
	'Define Enabled state
	Dim StateEnabled As ColorDrawable
	StateEnabled.Initialize2(Starter.AlphaColour1, 3, 2, Starter.BackColour1)

	Dim Sp As StateListDrawable
	Sp.Initialize
	Sp.AddState(Sp.State_Disabled, StateDisabled)
	Sp.AddState(Sp.State_Enabled, StateEnabled)
	Return Sp
	
End Sub

Sub AutoEditBox As StateListDrawable
	'Define Disabled state
	Dim StateDisabled As ColorDrawable
	StateDisabled.Initialize(Starter.AlphaColour2, 3)
	'Define Enabled state
	Dim StateEnabled As ColorDrawable
	StateEnabled.Initialize(Starter.BackColour1, 3)

	Dim EdBox As StateListDrawable
	EdBox.Initialize
	EdBox.AddState(EdBox.State_Disabled, StateDisabled)
	EdBox.AddState(EdBox.State_Enabled, StateEnabled)
	Return EdBox
	
End Sub

Sub EditBox As StateListDrawable
	'Define Disabled state
	Dim StateDisabled As ColorDrawable
	StateDisabled.Initialize(Starter.AlphaColour2, 3)
	'Define Enabled state
	Dim StateEnabled As ColorDrawable
	StateEnabled.Initialize(Starter.BackColour1, 3)

	Dim EdBox As StateListDrawable
	EdBox.Initialize
	EdBox.AddState(EdBox.State_Disabled, StateDisabled)
	EdBox.AddState(EdBox.State_Enabled, StateEnabled)
	Return EdBox
	
End Sub

Sub StdButton As StateListDrawable
	'Define Disabled state
	Dim StateDisabled As ColorDrawable
	StateDisabled.Initialize(Starter.AlphaColour2, 5)
	'Define Enabled state
	Dim StateEnabled As ColorDrawable
	StateEnabled.Initialize(Starter.BackColour1, 5)
	
	Dim StdBut As StateListDrawable
	StdBut.Initialize
	StdBut.AddState(StdBut.State_Disabled, StateDisabled)
	StdBut.AddState(StdBut.State_Enabled, StateEnabled)
	Return StdBut
	
End Sub

Sub ToggleButGood As StateListDrawable
	'Define Disabled state
	Dim StateDisabled As ColorDrawable
	StateDisabled.Initialize(Starter.AlphaColour1, 5)
	'Define Checked state
	Dim StateChecked As ColorDrawable
	StateChecked.Initialize2(Starter.BackColour2, 5, 3, Starter.HighlightColour)
	'Define Unchecked state
	Dim StateUnchecked As ColorDrawable
	StateUnchecked.Initialize(Starter.BackColour1, 5)

	Dim TogBut As StateListDrawable
	TogBut.Initialize
	TogBut.AddState(TogBut.State_Disabled, StateDisabled)
	TogBut.AddState(TogBut.State_Checked, StateChecked)
	TogBut.AddState(TogBut.State_Unchecked, StateUnchecked)
	Return TogBut
	
End Sub

Sub SetColours(Activ As Activity)
	' Set Colours
	For Each ViewObj As View In Activ.GetAllViewsRecursive
		Log(GetType(ViewObj))
		Select GetType(ViewObj)
			Case "android.widget.TextView"
				Dim lbl As Label
				lbl = ViewObj
				lbl .TextColor = Starter.TextColour
			Case "android.widget.EditText"
				Dim edt As EditText
				edt = ViewObj
				edt .Background = EditBox
			Case "android.widget.Button"
				Dim btn As Button
				btn = ViewObj
				btn .Background = StdButton
				btn .TextColor = Starter.TextColour
			Case "android.widget.ToggleButton"
				Dim tog As ToggleButton
				tog = ViewObj
				tog .Background = ToggleBut
				tog .TextColor = Starter.TextColour
			Case "anywheresoftware.b4a.objects.SpinnerWrapper$B4ASpinner"
				Dim spn As Spinner
				spn = ViewObj
				spn .Background = Spin
				spn .DropdownBackgroundColor = Starter.AlphaColour2
				spn .DropdownTextColor = Starter.TextColour
				spn .TextColor = Starter.TextColour
			Case "anywheresoftware.b4a.BALayout"
				Dim cdBorder As ColorDrawable
				cdBorder.Initialize2(Colors.Black, 5, 3, Starter.BorderColour)
				Dim Pnl As Panel
				Pnl = ViewObj
				Pnl .Background = cdBorder
			Case "android.widget.AutoCompleteTextView"
				Dim act As AutoCompleteEditText
				act = ViewObj
				act .Background = AutoEditBox
				act .TextColor = Starter.TextColour
			Case "android.widget.TabWidget"
			Case "android.webkit.WebView"
				Dim wbv As WebView
				wbv = ViewObj
				wbv .Color = Colors.Black
		End Select
	Next

End Sub