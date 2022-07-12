//
//  ViewController.swift
//  HelloWorld
//
//  Created by Mitul Vaghamshi on 2022-04-16.
//

import UIKit

class ViewController: UIViewController {
    @IBOutlet weak var enteredAge: UITextField!
    @IBOutlet weak var yearsLabel: UILabel!
        
    @IBAction func onCheckPressed(_ sender: UIButton) {
        if let age = Int(enteredAge.text!) {
            yearsLabel.text = age == 25 ? "Correct!" : "Wrong!";
        }
    }
}
