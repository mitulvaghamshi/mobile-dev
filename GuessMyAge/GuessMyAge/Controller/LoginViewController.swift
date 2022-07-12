//
//  LoginViewController.swift
//  HelloWorld
//
//  Created by Mitul Vaghamshi on 2022-04-17.
//

import UIKit
import CoreData
import WebKit
import Foundation

class LoginViewController: UIViewController {
    var isLoggedIn = false
    
    lazy var appDelegate: AppDelegate = {
        UIApplication.shared.delegate as! AppDelegate
    }()
    lazy var context: NSManagedObjectContext = {
        appDelegate.persistentContainer.viewContext
    }()
    
    @IBOutlet weak var usenameField: UITextField!
    @IBOutlet weak var passwordField: UITextField!
    
    @IBAction func onRegisterPressed(_ sender: UIBarButtonItem) {
        let values = NSEntityDescription.insertNewObject(forEntityName: "LoginEntity", into: context)
        
        if let user = usenameField.text {
            values.setValue(user, forKey: "username")
        }
        
        if let pass = passwordField.text {
            values.setValue(pass, forKey: "password")
        }
        
        do {
            try context.save()
        } catch {
            if let error = error as NSError? {
                fatalError("Unable to save: \(error), \(error.userInfo)")
            }
        }
    }
    
    @IBAction func onLoginPressed(_ sender: UIButton) {
        let request = NSFetchRequest<NSFetchRequestResult>(entityName: "LoginEntity")
        
        do {
            let results = try context.fetch(request)
            if results.count > 0 {
                for result in results as! [NSManagedObject] {
                    if !isLoggedIn {
                        if let username = result.value(forKey: "username") as? String {
                            isLoggedIn = username == usenameField.text!
                        } else {
                            isLoggedIn = false
                        }
                        if let password = result.value(forKey: "password") as? String {
                            isLoggedIn = password == passwordField.text!
                        } else {
                            isLoggedIn = false
                        }
                    } else { break }
                }
            }
        } catch {
            if let error = error as NSError? {
                fatalError("Unable to read: \(error), \(error.userInfo)")
            }
        }
        if isLoggedIn {
            self.navigationController?
                .pushViewController(ViewController(), animated: true)
        }
    }
    
    @IBOutlet weak var webKitView: WKWebView!
    
    @IBAction func openGoogle(_ sender: UIButton) {
        let url = URL(string: "https://google.com/")!

        webKitView.load(URLRequest(url: url))
    }
    
    
    @IBAction func makeApiCall(_ sender: UIButton) {
        let url = URL(string: "https://api.github.com/users/mitulvaghamshi")!
        let task = URLSession.shared.dataTask(with: url) { (data, response, error) in
            if let error = error as Error? {
                print(error)
            }
            if let data = data {
                print(data)
            }
        }
        task.resume()
    }
        
    override func viewDidLoad() {
        super.viewDidLoad()

        webKitView.loadHTMLString("<h1>Hello World!</h1>", baseURL: nil)
    }
}
