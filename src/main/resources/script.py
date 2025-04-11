import pandas as pd
import xml.etree.ElementTree as ET

# Parse the DMN file
tree = ET.parse('wa-task-initiation-privatelaw-prlapps.dmn')
root = tree.getroot()

# Define namespaces
ns = {
    'dmn': 'https://www.omg.org/spec/DMN/20191111/MODEL/',
    'biodi': 'http://bpmn.io/schema/dmn/biodi/2.0'
}

# Extract decision table data
decision_table = root.find('.//dmn:decisionTable', ns)
inputs = [input.find('dmn:inputExpression', ns).find('dmn:text', ns).text for input in decision_table.findall('dmn:input', ns)]
outputs = [output.get('name') for output in decision_table.findall('dmn:output', ns)]

# Create a DataFrame
data = {
    'Inputs': inputs,
    'Outputs': outputs
}
df = pd.DataFrame(data)

# Save to Excel
df.to_excel('dmn_decision_table.xlsx', index=False)
