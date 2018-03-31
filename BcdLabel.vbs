'	b-PAC 3.0 SDK Component Sample (Print Barcode Label)
'	(C)Copyright Brother Industries, Ltd. 2009
'
'<SCRIPT LANGUAGE="VBScript">

	' Get Item's name & code from arguments list
	set Args = Wscript.Arguments
	If Args.count < 3 Then
		MsgBox "N.o de argumentos passados para wscript menor que necessario"
		wscript.quit(1)
	End If
	sName = Args(0)
	sEmpresa = Args(1)
	sImpressora = Args(2)
	
	' Data Folder
	Const sDataFolder = "C:\Program Files\Brother bPAC3 SDK\Templates\"
	
	
	' Print
	DoPrint(sDataFolder & "latamrail.lbx")
	
	
	'*******************************************************************
	'	Print Module
	'*******************************************************************
    Sub DoPrint(strFilePath)
	
		Set ObjDoc = CreateObject("bpac.Document")
		bRet = ObjDoc.Open(strFilePath)
		
		If (bRet <> False) Then	

			ObjDoc.GetObject("objNome").Text = Replace(sName, "?", " ")
			ObjDoc.GetObject("objEmpresa").Text = Replace(sEmpresa, "?", " ")
			ObjDoc.SetMediaByName "29mm x 90mm", True		
			
			If Not objDoc.Printer Is Nothing Then
			
				impressoraNome = Replace(sImpressora, "?", " ")

				If impressoraNome = "Padrao" Then
				
					'*****************************************************
					' Imprimir na 1.a impressora disponivel
					'*****************************************************
					aPrinters = objDoc.Printer.getInstalledPrinters()

					If Not IsNull(aPrinters) Then				
											
						If ObjDoc.SetPrinter(aPrinters(0), True) Then
						
							If ObjDoc.StartPrint("", 0) Then
							
								if ObjDoc.PrintOut (1, 0) Then
								
									ObjDoc.EndPrint
								
								Else
								
									MsgBox "Nao eh possivel addicionar um print job", 0, "Atencao"
								
								End If						
							
							Else
							
								MsgBox "Erro", 0, "Atencao"
							
							End If
							
						Else
						
							MsgBox "Erro", 0, "Atencao"
						
						End If
						
					Else
					
						MsgBox "Instale pelo menos 1 impressora", 0, "Atencao"
						Exit Sub
					
					End If
				
				Else
				
					'*****************************************************
					'Imprimir na impressora que o usuario escolheu
					' QL-550
					' QL-570
					' QL-5502
					'*****************************************************
					If ObjDoc.SetPrinter(impressoraNome, True) Then
						
						If ObjDoc.StartPrint("", 0) Then
						
							if ObjDoc.PrintOut (1, 0) Then
							
								ObjDoc.EndPrint
							
							Else
							
								MsgBox "Nao eh possivel addicionar um print job", 0, "Atencao"
							
							End If						
						
						Else
						
							MsgBox "Erro", 0, "Atencao"
						
						End If
							
					Else
						
						MsgBox "Erro", 0, "Atencao"
						
					End If
				
				End If
				
			Else
			
				MsgBox "Nao se consegue instanciar objeto Printer", 0, "Atencao"
				Exit Sub
			
			End If
		
		Else
		
			MsgBox "Arquivo .lbx nao encontrado", 0, "Atencao"
			Exit Sub
		
		End If
		ObjDoc.Close
		Set ObjDoc = Nothing
	End Sub