#################################################################################################################################################################################
#                                                                                                                                                                               #
#   Contributor : JAT                                                                                                                                                           #
#   Dates : July 23rd - 24th, 2018                                                                                                                                              #
#   Given Task : Update "Domain Name" column to the name of each company's website / email domain name. This can be done in two ways, either by parsing the end of the email    #
#       strings present in column AH in the original document, or (where said emails are not present), just simply googling the company's name. Because that would be boring,   #
#       this script reads the name of the company to search and sends that as a Google query, wherein, the very first search result will be selected and parsed accordingly     #
#       and then the corresponding "domain" value will be overwritten in the table. The name of the source workbook is passed in via command line input.                        #
#   Approximate Runtime : 21 minutes (Ubuntu 18.04 VirtualBox)                                                                                                                  #
#                                                                                                                                                                               #
#################################################################################################################################################################################

from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from xlutils.copy import copy
import xlwt
import xlrd
import sys

driver = webdriver.Chrome()
chrome_options = Options()

# Commented out to show all the googling, can be made headless here
#chrome_options.add_argument("headless")

driver.set_page_load_timeout(5)
wait = WebDriverWait(driver, 100)
driver = webdriver.Chrome(chrome_options=chrome_options)

driver.get("http://www.google.com")

def googleSearch(term):
    element = driver.find_element_by_name("q")
    element.clear()
    element.send_keys(term)
    element.submit()
    selector = '//div/h3[@class="r"]/a'
    WebDriverWait(driver, 50).until(
        EC.visibility_of_element_located((By.XPATH, selector)))
    result = driver.find_element(By.XPATH, selector)
    return result.get_attribute("href")

def updateExcelWorkBook(bookName):
    rb = xlrd.open_workbook(bookName)
    filled_rows = range(1, rb.sheet_by_index(0).nrows)
    mainSheet = rb.sheet_by_index(0)
    wb = copy(rb)
    w_sheet = wb.get_sheet(0)
    for rowNum in filled_rows:
        companyName = mainSheet.cell(rowNum, 3).value
        email = mainSheet.cell(rowNum, 33).value
        if email != "":
            w_sheet.write(rowNum,4,email.split("@")[1])
        else:
            rawSite = googleSearch(companyName)
            if rawSite[0:4] == 'http' or rawSite[0:5] == 'https':
                rawSite = rawSite.split("://")[1]
            if rawSite[0:4] == 'www.':
                rawSite = rawSite.split("www.")[1]
            w_sheet.write(rowNum,4,rawSite.split("/")[0])
        wb.save("updated_"+bookName)

updateExcelWorkBook(sys.argv[1])

driver.close()