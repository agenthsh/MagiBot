#include, library\Gdip.ahk
#include, library\gdip_imagesearch.ahk

times:=10
pToken:=Gdip_Startup()

MsgBox,
(
Training System Started.
    Win+t : Training Multiple times
    Win+o : Training Once
    Win+c : Computer
    Win+f : Finish
)

#t::
    ; ������ ĸ�� ���α׷�(OBS)�� ������, ������ ĸ�ĸ� �����Ѵ�.
    ControlClick, x820 y676, ahk_class Qt5QWindowIcon,, LEFT

    startTime:=A_TickCount
    Loop %times% {
        training_once()
    }

    ; ������ ĸ�� ���α׷�(OBS)�� ������, ������ ĸ�ĸ� �����Ѵ�.
    ControlClick, x820 y676, ahk_class Qt5QWindowIcon,, LEFT

    duration:=A_TickCount-startTime
    MsgBox Training X %times% times has finished. Duration: %duration% ms

    return

#o::
    ; ������ ĸ�� ���α׷�(OBS)�� ������, ������ ĸ�ĸ� �����Ѵ�.
    ControlClick, x820 y676, ahk_class Qt5QWindowIcon,, LEFT

    training_once()
    duration:=A_TickCount-startTime

    ; ������ ĸ�� ���α׷�(OBS)�� ������, ������ ĸ�ĸ� �����Ѵ�.
    ControlClick, x820 y676, ahk_class Qt5QWindowIcon,, LEFT

    return

#c::
    startTime:=A_TickCount
    Loop %times% {
        computer()
    }

    duration:=A_TickCount-startTime
    MsgBox Computer X %times% times has finished. Duration: %duration% ms

    return

#f::
    ;; Exit
    Gdip_Shutdown(pToken)
    msgbox Training system has finished
    ExitApp

    return

training_once() {
    ; Run Eclipse
    ControlSend,,^{F11},workspace - Java

    ; CoordMode, Pixel, Window

    ; ������ ������ ������ Summary ȭ���� ����� ���� �� �����Ƿ�, ����Ű alt + o�� ������.
    ControlSend,,{Alt down}o{Alt up},ahk_class SWarClass

    ; Create Game ��ư�� ���ö� ���� ����Ѵ�.
    WinGet,hwnd,ID,Brood War
    Loop {
        if (imgSearch("images\create_game.bmp", hwnd, findX,findY)=true) {
            break
        } else {
            Sleep, 200
        }
    }
    ; Create Game ȭ���� ������, ����Ű alt + g�� ������.
    ControlSend,,{Alt down}g{Alt up},ahk_class SWarClass

    ; ���� ���� ȭ���� �����ٵ�, ����Ű alt + o�� ������.
    Sleep, 200
    ControlSend,,{Alt down}o{Alt up},ahk_class SWarClass

    ; ������ �����ϱ� ���ؼ� ����Ű alt + o�� ������.
    Sleep, 200
    ControlSend,,{Alt down}o{Alt up}{BACKSPACE},ahk_class SWarClass

    ; ������ ���۵� ������ ����Ű alt + o (OK)�� ������.
    Loop {
        if (imgSearch("images\play_screen.bmp", hwnd, findX,findY)=true) {
            break
        } else {
            Sleep, 1000
            ControlSend,,{Alt down}o{Alt up}{BACKSPACE},ahk_class SWarClass
        }
    }

    ; save_replay ȭ���� ���� ������ ����Ѵ�.
    Loop {
        if (imgSearch("images\save_replay.bmp", hwnd, findX,findY)=true) {
            break
        } else {
            Sleep, 200
        }
    }
}

computer() {
    ; Run Eclipse
    ControlSend,,^{F11},workspace - Java

    ; ������ ������ ������ Summary ȭ���� ����� ���� �� �����Ƿ�, ����Ű alt + o�� ������.
    WinActivate,ahk_class SWarClass
    ControlSend,,{Alt down}o{Alt up},ahk_class SWarClass

    ; Create Game ��ư�� ���ö� ���� ����Ѵ�.
    WinGet,hwnd,ID,Brood War
    Loop {
        if (imgSearch("images\create_game.bmp", hwnd, findX,findY)=true) {
            break
        } else {
            Sleep, 200
        }
    }
    ; Create Game ȭ���� ������, ����Ű alt + g�� ������.
    ControlSend,,{Alt down}g{Alt up},ahk_class SWarClass

    ; ���� ���� ȭ���� �����ٵ�, ����Ű alt + o�� ������.
    Sleep, 200
    ControlSend,,{Alt down}o{Alt up},ahk_class SWarClass

    ; �Ʊ��� �׶��� �����Ѵ�.
    Sleep, 200
    MouseClickDrag, LEFT, 500, 115, 500, 175
    MouseClickDrag, LEFT, 300, 150, 300, 245
    Sleep, 800
    ; ���� ����
    ; MouseClickDrag, LEFT, 500, 150, 500, 185
    ; �׶� ����
    ; MouseClickDrag, LEFT, 500, 150, 500, 215
    ; �����佺 ����
    MouseClickDrag, LEFT, 500, 150, 500, 250

    ; ������ �����ϱ� ���ؼ� ����Ű alt + o�� ������.
    Sleep, 200
    ControlSend,,{Alt down}o{Alt up}{BACKSPACE},ahk_class SWarClass

    ; ������ ���۵� ������ ����Ű alt + o (OK)�� ������.
    Loop {
        if (imgSearch("images\play_screen.bmp", hwnd, findX,findY)=true) {
            break
        } else {
            Sleep, 1000
            ControlSend,,{Alt down}o{Alt up}{BACKSPACE},ahk_class SWarClass
        }
    }

    ; ���� �ӵ��� ������ �����Ѵ�.
    ControlSend,,{Esc},ahk_class SWarClass
    Sleep, 100
    ControlSend,,{Enter}0{Enter},ahk_class SWarClass

    ; victory ȭ���� ���� ������ ����Ѵ�.
    ; draw.bmp, defeat.bmp, victory.bmp Ȱ�� ����.
    Loop {
        if (imgSearch("images\victory.bmp", hwnd, findX,findY)=true) {
            break
        } else {
            Sleep, 200
        }
    }
}

; ��ó: http://plorence.kr/209
imgSearch(image,hwnd, byref vx, byref vy) {
    pBitmapHayStack:=Gdip_BitmapFromHWND(hwnd)
    pBitmapNeedle:=Gdip_CreateBitmapFromFile(image)

    if Gdip_ImageSearch(pBitmapHayStack,pBitmapNeedle,list,0,0,0,0,32,,1,1) {
        StringSplit, LISTArray, LIST, `, 
        vx:=LISTArray1
        vy:=LISTArray2
        Gdip_DisposeImage(pBitmapHayStack), Gdip_DisposeImage(pBitmapNeedle)
        ;Gdip_Shutdown(pToken)
        return true
    }
    else 
    {
        Gdip_DisposeImage(pBitmapHayStack), Gdip_DisposeImage(pBitmapNeedle)
        ;Gdip_Shutdown(pToken)
        return false
    }
}
