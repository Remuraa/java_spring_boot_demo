package uemura.java_spring_boot_demo.contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url 'api/public/sales'
        headers { contentType(applicationJson()) }
        body('''
             {
                "description": "credit card",
            }
        ''')
    }
    response {
        status 204
        headers {
            contentType(applicationJson())
        }
    }
}