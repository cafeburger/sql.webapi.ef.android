using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using DemoAPI.EntityFramework;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace DemoAPI.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class CustomerController : ControllerBase
    {
        private readonly NorthwindContext _context;

        public CustomerController(NorthwindContext context)
        {
            _context = context;
        }


        [HttpGet]
        public async Task<IActionResult> GetAllCustomers()
        {
            return Ok( await _context.Customers.ToListAsync());

        }
        [HttpPost]
        public async Task<ActionResult> AddCustomer(Customer customer)
        {
            try
            {
                await _context.Customers.AddAsync(customer);
                _context.SaveChanges();
                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.InnerException.Message);
            }

        }
        [HttpPost]
        public async Task<ActionResult> UpdateCustomer(Customer customer)
        {
            try
            {
                _context.Customers.Attach(customer);
                _context.Customers.Update(customer);
                _context.SaveChanges();
                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }

        }
        [HttpGet]
        public async Task<ActionResult> RemoveCustomer(string CustomerId)
        {
            try
            {
                Customer customer = new Customer(){CustomerID = CustomerId};
                _context.Attach(customer);
                _context.Customers.Remove(customer);
                _context.SaveChanges();
                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }

        }
    }
}
