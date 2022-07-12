//
//  PhoneListViewController.swift
//  HelloWorld
//
//  Created by Mitul Vaghamshi on 2022-04-16.
//

import UIKit

class PhoneListViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    static let INDEX = "_index"
    static let USER_NAME = "_username"
    static let PHONE_NUMBER = "_phonenumber"
    static let data = UserDefaults.standard
    
    let total = data.integer(forKey: INDEX)
    var counter = 0
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return total
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = UITableViewCell(style: UITableViewCell.CellStyle.default, reuseIdentifier: "Cell")
 
        if let contact = Self.data.dictionary(forKey: String(counter)) {
            counter += 1
            cell.textLabel?.text = contact[Self.USER_NAME] as? String
        } else {
            cell.textLabel?.text = "No Data!"
        }
        return cell
    }
}
