import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url '/api/public/v1/sales'
        headers { contentType(applicationJson()) }
        body('''
             {
                "description": "credit card"
            }
        ''')
    }
    response {
        status 204
    }
}