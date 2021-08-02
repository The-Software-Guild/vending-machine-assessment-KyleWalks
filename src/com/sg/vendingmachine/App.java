package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingMachineController;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.sg.vendingmachine.ui.VendingMachineView;
import com.sg.vendingmachine.ui.UserIO;
import com.sg.vendingmachine.ui.UserIOConsoleImpl;

public class App {

    public static void main(String[] args) {
        UserIO myIo = new UserIOConsoleImpl();

        VendingMachineView myView = new VendingMachineView(myIo);

        VendingMachineDao myDao = new VendingMachineDaoFileImpl();

        VendingMachineController controller =
                new VendingMachineController(myDao, myView);

        controller.run();
    }
}
