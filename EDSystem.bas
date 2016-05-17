Type=Class
Version=4.3
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
'Class module
Sub Class_Globals
	Dim sysname As String
	Dim searchsysname As String
	Dim x, y, z As Double
	Dim sysgov As String
	Dim sysalg As String
	Dim syseco As Int
	Dim sysly As Double
End Sub

'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize(name As String)
	sysname = name
	searchsysname = name.ToLowerCase
	x = 0
	y = 0
	z = 0
	sysgov = ""
	sysalg = ""
	syseco = 0
	sysly = 0
End Sub

Public Sub setEDSystem(name As String, xcoord As Double, ycoord As Double, zcoord As Double, gov As String, alleg As String, econ As Int, ly As Double)
	sysname = name
	searchsysname = name.ToLowerCase
	x = xcoord
	y = ycoord
	z = zcoord
	sysgov = gov
	sysalg = alleg
	syseco = econ
	sysly = ly
End Sub

Sub setSysLY(ly As Double)
	sysly = ly
End Sub

Sub getSysLY As Double
	Return sysly
End Sub