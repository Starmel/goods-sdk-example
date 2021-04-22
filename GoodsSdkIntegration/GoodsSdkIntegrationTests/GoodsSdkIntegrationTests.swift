//
//  GoodsSdkIntegrationTests.swift
//  GoodsSdkIntegrationTests
//
//  Created by admin on 19.04.2021.
//

import XCTest
@testable import GoodsSdkIntegration
import GoodsSDK

class GoodsSdkIntegrationTests: XCTestCase {

    func testExample() throws {
        
        GoodsSDK().setup()
        
        GoodsSDK().cart.addProduct.invoke(name: "Pizza", price: 100) { (_, error) in
            XCTAssert(error == nil)
        }
        
        GoodsSDK().cart.getProducts.invoke { (products, _) in
            XCTAssert(products![0].name == "Pizza")
            XCTAssert(products![0].price == 100)
        }
    }
}
