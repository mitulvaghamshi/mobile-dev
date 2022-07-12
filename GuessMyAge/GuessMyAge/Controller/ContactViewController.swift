//
//  ResultViewController.swift
//  HelloWorld
//
//  Created by Mitul Vaghamshi on 2022-04-16.
//

import UIKit
import CoreData

class ContactViewController: UIViewController {
    static let INDEX = "_index"
    static let USER_NAME = "_username"
    static let PHONE_NUMBER = "_phonenumber"
    static let data = UserDefaults.standard

    @IBOutlet weak var nameField: UITextField!
    @IBOutlet weak var phoneField: UITextField!
    
    @IBAction func onSaveUserDefaults(_ sender: UIBarButtonItem) {
        var userData: [String:String] = [:]
        if let name = nameField.text {
            userData.updateValue(name, forKey: Self.USER_NAME)
        }
        if let phone = phoneField.text {
            userData.updateValue(phone, forKey: Self.PHONE_NUMBER)
        }

        let index = Self.data.integer(forKey: Self.INDEX)

        Self.data.setValue(userData, forKey: String(index))
        Self.data.setValue(index+1, forKey: Self.INDEX)

        nameField.text = ""
        phoneField.text = ""
    }
}
