#include, library\Gdip.ahk
#include, library\gdip_imagesearch.ahk

times:=10
pToken:=Gdip_Startup()

MsgBox,
(
Training System Started.
    Win+t : Training Multiple times
    Win+o : Training Once
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

    ; save_replay ȭ���� ���� ������ ����Ѵ�.
    Loop {
        if (imgSearch("images\save_replay.bmp", hwnd, findX,findY)=true) {
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

    if Gdip_ImageSearch(pBitmapHayStack,pBitmapNeedle,list,0,0,0,0,128,,1,1) {
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
