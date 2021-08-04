package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingMachineController;
import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.sg.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.sg.vendingmachine.ui.VendingMachineView;
import com.sg.vendingmachine.ui.UserIO;
import com.sg.vendingmachine.ui.UserIOConsoleImpl;

public class App {

    public static void main(String[] args) {
        UserIO myIo = new UserIOConsoleImpl();

        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();

        VendingMachineView myView = new VendingMachineView(myIo);

        VendingMachineDao myDao = new VendingMachineDaoFileImpl();

        VendingMachineServiceLayerImpl myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao);

        VendingMachineController controller =
                new VendingMachineController(myService, myView);

        controller.run();

        myIo.print("\nExiting");
    }
}
