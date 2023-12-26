from pathlib import Path

import networkx as nx


class Workflow:
    def __init__(self, line: str):
        self.workflow_name, workflow_text = line.split('{')
        workflow_text = workflow_text[:-1]

        self.rules = []
        for rule in workflow_text.split(','):
            if ':' in rule:
                condition, behavior = rule.split(':')
                category = condition[0]
                comparator = condition[1]
                threshold = int(condition[2:])
                self.rules.append((category, comparator, threshold, behavior))
            else:
                self.default_behavior = rule

    def evaluate(self, part: dict[str, int]) -> str:
        for category, comparator, threshold, behavior in self.rules:
            part_category = part[category]
            if comparator == '>' and part_category > threshold:
                return behavior
            if comparator == '<' and part_category < threshold:
                return behavior
        return self.default_behavior

    def build_tree(self, g: nx.MultiDiGraph):
        x_range = range(1, 4001)
        m_range = range(1, 4001)
        a_range = range(1, 4001)
        s_range = range(1, 4001)

        for category, comparator, threshold, behavior in self.rules:
            follow_x = x_range
            follow_m = m_range
            follow_a = a_range
            follow_s = s_range

            match (category, comparator):
                case 'x', '>':
                    follow_x = range(threshold+1, x_range.stop)
                    x_range = range(x_range.start, threshold)
                case 'x', '<':
                    follow_x = range(x_range.start, threshold)
                    x_range = range(threshold+1, x_range.stop)
                case 'm', '>':
                    follow_m = range(threshold+1, m_range.stop)
                    m_range = range(m_range.start, threshold)
                case 'm', '<':
                    follow_m = range(m_range.start, threshold)
                    m_range = range(threshold+1, m_range.stop)
                case 'a', '>':
                    follow_a = range(threshold+1, a_range.stop)
                    a_range = range(a_range.start, threshold)
                case 'a', '<':
                    follow_a = range(a_range.start, threshold)
                    a_range = range(threshold+1, a_range.stop)
                case 's', '>':
                    follow_s = range(threshold+1, s_range.stop)
                    s_range = range(s_range.start, threshold)
                case 's', '<':
                    follow_s = range(s_range.start, threshold)
                    s_range = range(threshold+1, s_range.stop)

            g.add_edge(self.workflow_name, behavior, x=follow_x, m=follow_m, a=follow_a, s=follow_s)

        g.add_edge(self.workflow_name, self.default_behavior, x=x_range, m=m_range, a=a_range, s=s_range)


class Interpreter:
    def __init__(self):
        self.workflows: dict[str, Workflow] = {}

    def add_workflow(self, line: str):
        workflow = Workflow(line)
        self.workflows[workflow.workflow_name] = workflow

    def evaluate(self, part: dict[str, int]) -> bool:
        behavior = 'in'
        while True:
            if behavior == 'R':
                return False
            if behavior == 'A':
                return True
            workflow = self.workflows[behavior]
            behavior = workflow.evaluate(part)

    def build_tree(self) -> nx.MultiDiGraph:
        g = nx.MultiDiGraph()
        for flow in self.workflows.values():
            flow.build_tree(g)
        return g


def read_input() -> (Interpreter, [dict[str, int]]):
    interpreter = Interpreter()
    parts = []

    input_path = Path(__file__).parent.joinpath('input').joinpath('day19.txt')
    with input_path.open('r') as input_file:
        for line in input_file.readlines():
            line = line.strip()
            if line.startswith('{'):
                part = {}
                line = line[1:-1]
                for pair in line.split(','):
                    key, val = pair.split('=')
                    part[key] = int(val)
                parts.append(part)
            elif len(line) > 0:
                interpreter.add_workflow(line)

    return interpreter, parts


def part1(interpreter: Interpreter, parts: [dict[str, int]]) -> int:
    acc = 0
    for part in parts:
        if interpreter.evaluate(part):
            acc += sum(part.values())
    return acc


def part2(interpreter: Interpreter) -> int:
    g = interpreter.build_tree()
    accumulators = {}

    for path in nx.all_simple_paths(g, 'in', 'A'):
        x = range(1, 4001)
        m = range(1, 4001)
        a = range(1, 4001)
        s = range(1, 4001)
        for i, node in enumerate(path):
            if i > 0:
                previous_node = path[i-1]
                edge_data = g.get_edge_data(previous_node, node)

                x_min = min([a['x'].start for a in edge_data.values()])
                x_max = max([a['x'].stop for a in edge_data.values()])
                x = range(max(x_min, x.start), min(x_max, x.stop))

                m_min = min([a['m'].start for a in edge_data.values()])
                m_max = max([a['m'].stop for a in edge_data.values()])
                m = range(max(m_min, m.start), min(m_max, m.stop))

                a_min = min([a['a'].start for a in edge_data.values()])
                a_max = max([a['a'].stop for a in edge_data.values()])
                a = range(max(a_min, a.start), min(a_max, a.stop))

                s_min = min([a['s'].start for a in edge_data.values()])
                s_max = max([a['s'].stop for a in edge_data.values()])
                s = range(max(s_min, s.start), min(s_max, s.stop))

        print(path, x, m, a, s)
        key = ','.join(path)
        best = accumulators.get(key, 0)
        last = len(x) * len(m) * len(a) * len(s)
        if last > best:
            accumulators[key] = last

    return sum(accumulators.values())


def main():
    interpreter, parts = read_input()
    print('=====================================')
    print(f'Part 1:\t{part1(interpreter, parts)}')
    print(f'Part 2:\t{part2(interpreter)}')
    print('=====================================')


if __name__ == '__main__':
    main()
